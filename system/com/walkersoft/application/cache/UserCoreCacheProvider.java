package com.walkersoft.application.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.walker.infrastructure.cache.AbstractCacheProvider;
import com.walker.infrastructure.cache.support.Cachable;
import com.walker.infrastructure.cache.support.Cache;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.system.dao.UserDao;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.system.pojo.SysOperatorImp;
import com.walkersoft.system.pojo.UserGroup;

/**
 * 系统用户缓存提供者实现
 * @author shikeying
 * @date 2013-11-8
 *
 */
public class UserCoreCacheProvider extends AbstractCacheProvider<UserCoreEntity> {

//	private DepartmentCacheProvider departmentCache = (DepartmentCacheProvider)CacheManager.getCacheTreeProvider(DepartmentEntity.class);
	
	private DepartmentCacheProvider departmentCache = null;
	
	public void setDepartmentCache(DepartmentCacheProvider departmentCache) {
		this.departmentCache = departmentCache;
	}

	@Autowired
	private UserDao userDao;
	
	@Override
	public String getProviderName() {
		// TODO Auto-generated method stub
		return "system.cache.user";
	}

	@Override
	public Class<UserCoreEntity> getProviderType() {
		// TODO Auto-generated method stub
		return UserCoreEntity.class;
	}

	@Override
	protected int loadDataToCache(Cache cache) {
		// TODO Auto-generated method stub
		if(userDao == null)
			throw new IllegalStateException("userDao is required!");
		List<UserCoreEntity> list = userDao.getAllUserList();
		if(list != null){
			for(UserCoreEntity entity : list){
				cache.put(entity.getId(), entity);
//				allUsers.add(entity);
			}
			return list.size();
		}
		return 0;
	}

	/**
	 * 更新缓存，设置用户为'已删除'
	 * @param ids
	 */
	public void setUsersDelete(String[] ids){
		if(ids != null && ids.length > 0){
			for(String id : ids){
				this.getCacheData(id).setStatus(UserCoreEntity.STATUS_DELETE);
			}
		}
	}
	
	public void removeUsers(String[] ids){
		if(ids != null && ids.length > 0){
			for(String id : ids)
				this.removeCacheData(id);
		}
	}
	
//	private static List<UserCoreEntity> allUsers = new ArrayList<UserCoreEntity>();
	
	public List<UserCoreEntity> getAllUsers(){
		List<UserCoreEntity> allUsers = new ArrayList<UserCoreEntity>();
		UserCoreEntity u = null;
		for(Iterator<Cachable> i = this.getCache().getIterator(); i.hasNext();){
			/* 排除掉超级管理员 和 外部用户*/
			u = (UserCoreEntity)(i.next().getValue());
			if(u.isSupervisor() || u.isFromOuter()) continue;
			allUsers.add(u);
		}
		return allUsers;
	}
	
	public Map<String, UserGroup> getUsersMenuGroup(){
		List<String> _group = new ArrayList<String>();
		Map<String, UserGroup> _groupMap = new HashMap<String, UserGroup>();
		
		List<UserCoreEntity> usrLst = getAllUsers();
		
		for(UserCoreEntity _user : usrLst){
			String orgName = getAvailableOrgName(_user);
			if(!_group.contains(orgName)){
				_group.add(orgName);
				UserGroup _ug = new UserGroup();
				_ug.setGroupName(orgName);
				_groupMap.put(orgName, _ug);
			}
		}
		String orgName = null;
		for(UserCoreEntity _user : usrLst){
			orgName = getAvailableOrgName(_user);
			SysOperatorImp u = new SysOperatorImp();
			u.setUserId(_user.getId());
			u.setUserName(_user.getName());
			u.setGroup(orgName);
			_groupMap.get(orgName).addUserObj(u);
		}
		
		return _groupMap;
	}
	
	/**
	 * 得到用户有效的机构名称，如果直接在单位下面就返回单位名称，否则返回部门名称
	 * @param user
	 * @return
	 */
	private String getAvailableOrgName(UserCoreEntity user){
		if(user.isFromOuter()){
			// 外部用户，直接使用单位名字字段
			String orgName = user.getOrgName();
			return orgName == null? StringUtils.EMPTY_STRING : orgName;
		}
		if(StringUtils.isNotEmpty(user.getDepartmentId())){
			return departmentCache.getDepartmentName(user.getDepartmentId());
		} else
			return departmentCache.getDepartmentName(user.getOrgId());
	}
	
	public String getUserName(String id){
		assert(StringUtils.isNotEmpty(id));
		UserCoreEntity user = this.getCacheData(id);
		if(user == null)
			throw new RuntimeException("user not found in cache: " + id);
		return user.getName();
	}
	
	/**
	 * 判断一个机构中是否存在可用用户，不包括'已删除'用户</p>
	 * 该方法在用户量加大（如：上万）时需要优化，<code>CacheProvider</code>也需要重新实现，</br>
	 * 否则遍历所有用户将会非常耗费资源。
	 * @param orgId
	 * @return
	 */
	public boolean hasUserInDepartment(String orgId){
		assert (StringUtils.isNotEmpty(orgId));
		for(UserCoreEntity u : getAllUsers()){
			if(u.isDeleted()) continue;
			if(u.getOrgId().equals(orgId)){
				return true;
			}
			if(StringUtils.isNotEmpty(u.getDepartmentId()) 
					&& u.getDepartmentId().equals(orgId)){
				return true;
			}
		}
		return false;
	}
}
