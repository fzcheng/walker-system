package com.walkersoft.application.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.infrastructure.cache.tree.AbstractCacheTreeProvider;
import com.walker.infrastructure.cache.tree.CacheTreeLoadCallback;
import com.walker.infrastructure.cache.tree.CacheTreeNode;
import com.walker.infrastructure.cache.tree.DefaultCacheTreeNode;
import com.walkersoft.system.dao.DepartmentDao;
import com.walkersoft.system.entity.DepartmentEntity;

/**
 * 组织机构缓存提供者实现
 * @author shikeying
 *
 */
public class DepartmentCacheProvider extends AbstractCacheTreeProvider<DepartmentEntity> {

	private static final String DEPARTMENT_CACHE_NAME = "system.cache.department";
	
	@Autowired
	private DepartmentDao departmentDao;
	
	/* 不需要加载已删除机构树，的回调实现 */
	private static final CacheTreeLoadCallback callback = new NotWantedDeleteCallback();
	
	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return DEPARTMENT_CACHE_NAME;
	}

	@Override
	public Class<DepartmentEntity> getProviderType() {
		// TODO Auto-generated method stub
		return DepartmentEntity.class;
	}

	@Override
	protected Map<String, CacheTreeNode> loadChildList() {
		// TODO Auto-generated method stub
		List<DepartmentEntity> allChildList = departmentDao.getAllDepartmentList();
		return obtainMap(allChildList);
	}

	@Override
	protected Map<String, CacheTreeNode> loadRootList() {
		// TODO Auto-generated method stub
		List<DepartmentEntity> rootList = departmentDao.getRootList();
		return obtainMap(rootList);
	}

	@Override
	protected CacheTreeNode toCacheTreeNode(DepartmentEntity entity) {
		DefaultCacheTreeNode node = new DefaultCacheTreeNode(entity.getId()
				, entity.getName(), entity, entity.getParentId(), new Long(entity.getOrderNum()).intValue());
		if(entity.isPost()){
			node.setIcon("3.png");
		}
		return node;
	}

	private Map<String, CacheTreeNode> obtainMap(List<DepartmentEntity> list){
		if(list != null && list.size() > 0){
			Map<String, CacheTreeNode> map = new TreeMap<String, CacheTreeNode>();
			for(DepartmentEntity code : list){
				map.put(code.getId(), toCacheTreeNode(code));
			}
			return map;
		} else
			return null;
	}
	
	//============================================
	// 缓存对象自定义方法，仅限于机构模块应用
	//============================================
	
	/**
	 * 返回所有单位列表（树），不包含已删除节点。<br>
	 * 该方法开发较早，当时机构并不支持单位嵌套，因此默认第一级都是单位。<br>
	 * 后来加入了单位嵌套单位，因此后续方法中可以返回树型的单位机构信息。
	 * @return
	 */
	public List<CacheTreeNode> getAvailableRootList(){
		Collection<CacheTreeNode> rootList = this.getRootList();
		if(rootList == null) return null;
		List<CacheTreeNode> list = new ArrayList<CacheTreeNode>();
		for(CacheTreeNode node : rootList){
			if(callback.decide(node) != null)
				list.add(node);
		}
		return list;
	}
	
	public static class NotWantedDeleteCallback implements CacheTreeLoadCallback{
		@Override
		public CacheTreeNode decide(CacheTreeNode node) {
			// TODO Auto-generated method stub
			if(node != null){
				Object source = node.getSource();
				if(source != null){
					DepartmentEntity entity = (DepartmentEntity)source;
					if(entity.getStatus() == 1)
						return null;
				} else
					throw new NullPointerException("source not found.");
			}
			return node;
		}
	}
	
	/**
	 * 根据机构树中某个节点找出其对应根节点对象
	 * @param childId 当前的节点ID
	 * @return
	 */
	public DepartmentEntity getRootTreeNode(String childId){
		DepartmentEntity node = this.getCacheData(childId);
		if(node == null)
			throw new NullPointerException("not found CacheTreeNode: " + childId);
		if(node.getType() == DepartmentEntity.TYPE_ORG)
			return node;
		String parentId = node.getParentId();
		return getRootTreeNode(parentId);
	}
	
	public String getDepartmentName(String id){
		DepartmentEntity e = this.getCacheData(id);
		if(e == null)
			throw new NullPointerException("not found department in cache: " + id);
		return e.getName();
	}
}
