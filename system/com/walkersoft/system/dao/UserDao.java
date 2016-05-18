package com.walkersoft.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.walker.db.PropertyEntry;
import com.walker.db.Sorts;
import com.walker.db.Sorts.Sort;
import com.walker.db.hibernate.PojoDaoSupport;
import com.walker.db.page.support.GenericPager;
import com.walker.db.page.support.MapPager;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.MyApplicationConfig;
import com.walkersoft.system.entity.UserCoreEntity;

@Repository("userDao")
public class UserDao extends PojoDaoSupport<UserCoreEntity> {

	public static final String HQL_SEARCH_USER1 = "select u from UserCoreEntity u where u.status = 0 and u.fromType = 0";
	public static final String HQL_SEARCH_USER2 = "select u from UserCoreEntity u where u.status = 0 and u.fromType = 0 and u.orgId = ?";
	public static final String HQL_SEARCH_USER3 = "select u from UserCoreEntity u where u.status = 0 and u.fromType = 0 and u.departmentId = ?";
	/* 获取外部用户 */
	public static final String HQL_SEARCH_OUTER = "select u from UserCoreEntity u where u.status = 0 and u.fromType = 1";
	
	public static final Sort sort = Sorts.ASC().setField("orderNum");
	
	private static final String SQL_MAX_ORDER = "select max(entity.orderNum) from UserCoreEntity entity"
			+ " where entity.orgId = ?";
	
	/**
	 * 分页获取机构下用户集合
	 * @param orgId 单位ID
	 * @param departmentId 部门ID
	 * @return
	 */
	public GenericPager<UserCoreEntity> getUserList(String orgId, String departmentId){
		if(StringUtils.isNotEmpty(orgId))
			return this.queryForpage(HQL_SEARCH_USER2, new Object[]{orgId}, sort);
		else if(StringUtils.isNotEmpty(departmentId)){
			return this.queryForpage(HQL_SEARCH_USER3, new Object[]{departmentId}, sort);
		} else
			return this.queryForpage(HQL_SEARCH_USER1, new Object[]{}, sort);
	}
	
	/**
	 * 返回外部用户列表
	 * @return
	 */
	public GenericPager<UserCoreEntity> getOuterUserList(){
		return this.queryForpage(HQL_SEARCH_OUTER, new Object[]{}, sort);
	}
	
	public long getMaxOrderNum(String orgId){
		assert (StringUtils.isNotEmpty(orgId));
		Long max = this.findMathExpress(SQL_MAX_ORDER, new Object[]{orgId});
		return (max == null ? 1 : max.intValue()+1);
	}
	
	public UserCoreEntity getUserByLoginId(String loginId){
		assert (StringUtils.isNotEmpty(loginId));
		if(isSupervisor(loginId))
			return getSupervisor();
		return this.findUniqueBy("loginId", loginId);
	}
	
	public List<UserCoreEntity> getAllUserList(){
		List<UserCoreEntity> userList = this.findBy(sort);
		userList.add(getSupervisor());
		return userList;
	}
	
	/**
	 * 返回单位下用户集合
	 * @param orgId 单位ID
	 * @return
	 */
	public List<UserCoreEntity> getOrgUserList(String orgId){
		return this.findBy(PropertyEntry.createEQ("orgId", orgId), sort);
	}
	
	/**
	 * 返回部门下用户集合
	 * @param deptId 部门ID
	 * @return
	 */
	public List<UserCoreEntity> getDepartmentUserList(String deptId){
		return this.findBy(PropertyEntry.createEQ("departmentId", deptId), sort);
	}
	
	/**
	 * 返回系统超级管理员对象
	 * @return
	 */
	private UserCoreEntity getSupervisor(){
		return MyApplicationConfig.getSupervisor();
	}
	private boolean isSupervisor(String loginId){
		if(loginId.equalsIgnoreCase(MyApplicationConfig.getSupervisorName()))
			return true;
		return false;
	}
	
	private static final String SQL_USER_ROLES = "select t.role_id from s_role_user t where t.user_id = ?";
	
	/**
	 * 返回给定用户所在的角色ID数组
	 * @param userId 用户ID（主键）
	 * @return
	 */
	public String[] getUserRoles(String userId){
		List<Map<String, Object>> roleIds = this.sqlQueryForList(SQL_USER_ROLES, new Object[]{userId});
		if(roleIds != null){
			String[] ids = new String[roleIds.size()];
			int i = 0;
			for(Map<String, Object> map : roleIds){
				ids[i] = map.get("role_id").toString();
				i++;
			}
			return ids;
		}
		return null;
	}
	
	private static final String SQL_EDIT_PASSWORD = "update UserCoreEntity u set u.password = ? where u.id = ?";
	
	public void updatePassword(String userId, String password){
		this.bulkUpdate(SQL_EDIT_PASSWORD, new Object[]{password, userId});
	}
	
	private static final String SQL_USER_PAGER_MAP = "select * from s_user_core order by order_num asc";
	
	/**
	 * 分页返回用户列表，返回Map对象集合，map中存放记录中所有字段。
	 * @return
	 */
	public MapPager getPagerUserList(int pageIndex, int pageSize){
		return this.sqlQueryForPage(SQL_USER_PAGER_MAP, null, pageIndex, pageSize, null);
	}
}
