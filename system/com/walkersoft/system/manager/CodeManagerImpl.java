package com.walkersoft.system.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.cache.CodeCacheProvider;
import com.walkersoft.system.dao.CodeDao;
import com.walkersoft.system.entity.CodeEntity;

@Service("codeManager")
public class CodeManagerImpl {

	@Autowired
	private CodeDao codeDao;
	
	private static final String QUERY_CODE_TABLE_PAGE 
		= "select code from CodeEntity code where code.codeType = 0";
	
	/* 默认的排序对象，按照顺序号正序排列 */
	private static final Sort sxhSort = Sorts.ASC().setField("sxh");
	
//	private CacheTree<CodeEntity> codeCacheTree = SimpleCacheManager.getCacheTreeProvider(CodeEntity.class);
	@Autowired
	private CodeCacheProvider codeCacheProvider;
	
	public GenericPager<CodeEntity> queryPageCodeTables(){
		return codeDao.queryForpage(QUERY_CODE_TABLE_PAGE, null, sxhSort);
	}
	
	/**
	 * 找出代码表下一级数据集合
	 * @param codeTableId
	 * @return
	 */
	public List<CodeEntity> queryOneLvlItem(String codeTableId){
		assert (StringUtils.isNotEmpty(codeTableId));
		return codeDao.findBy(new PropertyEntry[]{
				PropertyEntry.createEQ("codeType", 1),
				PropertyEntry.createEQ("parentId", codeTableId)}
			, new Sort[]{sxhSort});
	}
	
	/**
	 * 保存新的代码项，主键自动生成。
	 * @param entity
	 */
	public void execInsertCodeItem(CodeEntity entity){
		long maxSxh = codeDao.findMax("sxh");
		entity.setSxh(maxSxh);
		codeDao.save(entity);
		codeDao.updateParentCodeChildSum(entity.getParentId());
		codeCacheProvider.putCacheData(entity.getId(), entity);
	}
	
	public void execUpdateCodeItem(CodeEntity entity){
		CodeEntity old = codeDao.get(entity.getId());
		old.setName(entity.getName());
		old.setCodeId(entity.getCodeId());
		old.setCodeSec(entity.getCodeSec());
		codeDao.updateEntity(old);
		codeCacheProvider.updateCacheData(entity.getId(), old);
	}
	
	/**
	 * 删除代码项信息
	 * @throws Exception 
	 */
	public void execDeleteCodeInfo(String codeId,String parentId) throws Exception{
		assert (StringUtils.isNotEmpty(codeId));
		codeDao.removeById(codeId);
		if(StringUtils.isNotEmpty(parentId) && !parentId.equals("null")){
			codeDao.decreaseParentCodeChildSum(parentId);
		}
		codeCacheProvider.removeCacheData(codeId);
	}
	
	public CodeEntity queryExist(String id){
		return codeDao.get(id);
	}
}
