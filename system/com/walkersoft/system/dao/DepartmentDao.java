package com.walkersoft.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.PojoDaoSupport;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.entity.DepartmentEntity;

/**
 * 因为缓存要使用该DAO，所以提前实例化该对象，实现<code>FactoryBean</code>。
 * @author shikeying
 *
 */
@Repository("departmentDao")
public class DepartmentDao extends PojoDaoSupport<DepartmentEntity> {

	private static final String SQL_MAX_ORDER_ORG = "select max(entity.orderNum) from DepartmentEntity entity"
			+ " where entity.type = 0";
	
	private static final String SQL_MAX_ORDER_DEP = "select max(entity.orderNum) from DepartmentEntity entity"
			+ " where entity.type = 1 and entity.parentId = ?";
	
	private static Sort sxhSort = Sorts.ASC().setField("orderNum");
	
	public long getMaxOrderNum(DepartmentEntity entity){
		assert (entity != null);
		String parentId = entity.getParentId();
		long result = 0;
		if(StringUtils.isEmpty(parentId) || parentId.equals("0")){
			// 是单位
			result = this.findMathExpress(SQL_MAX_ORDER_ORG);
		} else {
			// 是部门
			result = this.findMathExpress(SQL_MAX_ORDER_DEP, new Object[]{parentId});
		}
//		return (result + 1);
		return (result+1);
	}
	
	public List<DepartmentEntity> getRootList(){
		return this.findBy(PropertyEntry.createEQ("type", 0), sxhSort);
	}
	
	/**
	 * 返回所有非单位（子部门）集合。<br>
	 * 
	 * 包含：部门下岗位，2015-2-28 时克英添加
	 * @return
	 */
	public List<DepartmentEntity> getAllDepartmentList(){
//		return this.findBy(PropertyEntry.createEQ("type", 1), sxhSort);
		return this.findBy(PropertyEntry.createNE("type", 0), sxhSort);
	}

//	@Override
//	public DepartmentDao getObject() throws Exception {
//		return this;
//	}
//
//	@Override
//	public Class<?> getObjectType() {
//		return DepartmentDao.class;
//	}
//
//	@Override
//	public boolean isSingleton() {
//		return true;
//	}
}
