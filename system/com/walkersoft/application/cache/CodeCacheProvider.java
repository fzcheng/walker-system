package com.walkersoft.application.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.infrastructure.cache.tree.AbstractCacheTreeProvider;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.cache.tree.DefaultCacheTreeNode;
import com.walkersoft.system.dao.CodeDao;
import com.walkersoft.system.entity.CodeEntity;

/**
 * 代码缓存提供者实现
 * @author shikeying
 *
 */
public class CodeCacheProvider extends AbstractCacheTreeProvider<CodeEntity> {

	private static final String CODE_CACHE_NAME = "system.cache.code";
	
	@Autowired
	private CodeDao codeDao;
	
	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return CODE_CACHE_NAME;
	}

	@Override
	public Class<CodeEntity> getProviderType() {
		// TODO Auto-generated method stub
		return CodeEntity.class;
	}

	@Override
	protected Map<String, CacheTreeNode> loadRootList() {
		// TODO Auto-generated method stub
		List<CodeEntity> list = codeDao.getRootCodeList();
		return obtainMap(list);
	}

	@Override
	protected Map<String, CacheTreeNode> loadChildList() {
		// TODO Auto-generated method stub
		List<CodeEntity> list = codeDao.getAllCodeItemList();
		return obtainMap(list);
	}
	
	@Override
	protected CacheTreeNode toCacheTreeNode(CodeEntity entity){
		return new DefaultCacheTreeNode(entity.getId()
				, entity.getName(), entity, entity.getParentId());
	}

	private Map<String, CacheTreeNode> obtainMap(List<CodeEntity> list){
		if(list != null && list.size() > 0){
			Map<String, CacheTreeNode> map = new TreeMap<String, CacheTreeNode>();
			for(CodeEntity code : list){
				map.put(code.getId(), toCacheTreeNode(code));
			}
			return map;
		} else
			return null;
	}
	
	/**
	 * 返回代码集合对象，通常在数据库中可能会存储多个代码ID，可以通过此 方法获取代码集合。
	 * @param codeIds 输入多个代码ID数组
	 * @return
	 */
	public List<CacheTreeNode> getCodeList(String[] codeIds){
		List<CacheTreeNode> result = new ArrayList<CacheTreeNode>(2*2);
		if(codeIds != null && codeIds.length > 0){
			for(String cid : codeIds){
				result.add(this.get(cid));
			}
		}
		return result;
	}
	
	/**
	 * 返回给定代码表的子代码项，树结构
	 * @param codeTableName 代码表ID
	 * @return
	 */
	public List<CacheTreeNode> getCodeChildrenList(String codeTableName){
		CacheTreeNode node = this.getOneRootNode(codeTableName);
		if(node == null){
			throw new IllegalArgumentException("not found node: " + codeTableName);
		}
		Collection<CacheTreeNode> list = node.getChildren();
		if(list != null){
			List<CacheTreeNode> result = new ArrayList<CacheTreeNode>();
			for(CacheTreeNode n : list){
				result.add(n);
			}
			return result;
		}
		return null;
	}
}
