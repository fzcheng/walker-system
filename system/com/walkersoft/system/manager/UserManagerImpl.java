package com.walkersoft.system.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.PropertyEntry;
import com.walker.db.page.support.GenericPager;
import com.walker.db.page.support.MapPager;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.UserExistException;
import com.walkersoft.application.util.DepartmentUtils;
import com.walkersoft.system.callback.SystemUserCallback;
import com.walkersoft.system.dao.FunctionDao;
import com.walkersoft.system.dao.RoleDao;
import com.walkersoft.system.dao.UserDao;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.entity.UserCoreEntity;

@Service("userManager")
public class UserManagerImpl {

	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private FunctionDao functionDao;
	
	public GenericPager<UserCoreEntity> queryAllUser(){
		return userDao.getUserList(null, null);
	}
	
	/**
	 * 返回一个单位下所有用户列表
	 * @param orgId
	 * @return
	 */
	public GenericPager<UserCoreEntity> queryOrgUser(String orgId){
		return userDao.getUserList(orgId, null);
	}
	
	public GenericPager<UserCoreEntity> queryOuterUser(){
		return userDao.getOuterUserList();
	}
	
	/**
	 * 返回一个部门下所有用户列表
	 * @param deptId
	 * @return
	 */
	public GenericPager<UserCoreEntity> queryDeptUser(String deptId){
		return userDao.getUserList(null, deptId);
	}
	
	public UserCoreEntity queryUserByLoginId(String loginId){
		UserCoreEntity user = userDao.getUserByLoginId(loginId);
		if(user != null){
			String[] roleIds = userDao.getUserRoles(user.getId());
			if(roleIds != null) user.setRoleIds(roleIds);
		}
		return user;
	}
	
	public UserCoreEntity queryUser(String id){
		return userDao.get(id);
	}
	
	private static final String MSG_USER_EXIST = "当前登录ID已经存在";
	
	public UserCoreEntity execSaveUserStyle(String userId, String style){
		if(StringUtils.isNotEmpty(style)){
			UserCoreEntity user = userDao.get(userId);
			user.setStyle(style);
			userDao.updateEntity(user);
			return user;
		}
		return null;
	}
	
	public String execSaveUser(UserCoreEntity entity, SystemUserCallback callback){
		return doSave(entity, null, callback);
	}
	
	/**
	 * 更新用户
	 * @param entity
	 * @param roleIds
	 * @param initPassword 默认初始化密码，如果修改loginId，则必须更新密码为初始密码，盐值改变了，没法
	 * @return
	 * @throws UserExistException
	 */
	public UserCoreEntity execUpdateUser(UserCoreEntity entity
			, String[] roleIds, SystemUserCallback callback, String initPassword) throws UserExistException{
		List<UserCoreEntity> existList = userDao.findBy(PropertyEntry.createEQ("loginId", entity.getLoginId()), null);
		if(existList != null && existList.size() > 1){
			throw new UserExistException("用户已经存在: " + entity.getLoginId());
		}
		UserCoreEntity loaded = userDao.get(entity.getId());
		if(!entity.getLoginId().equals(loaded.getLoginId())){
			// 修改了登录ID，盐值改变，必须更新密码为初始值
			loaded.setPassword(initPassword);
			logger.debug("修改用户时，变更了loginId: " + entity.getLoginId() + ", 盐值改变，因此密码必须重新设置为初始密码");
		}
		
		loaded.setLoginId(entity.getLoginId());
		loaded.setName(entity.getName());
		loaded.setType(entity.getType());
		loaded.setEnable(entity.getEnable());
		loaded.setSummary(entity.getSummary());
		userDao.updateEntity(loaded);
		
		if(roleIds != null){
			functionDao.deleteRoleByUserId(entity.getId());
			functionDao.insertRoleByUserId(entity.getId(), roleIds);
		}
		// 调用回调接口修改集成的用户信息
		if(callback != null){
			callback.onUpdateUser(entity);
		}
		return loaded;
	}
	
	public String execSaveUser(UserCoreEntity entity, String[] roleIds, SystemUserCallback callback){
		return doSave(entity, roleIds, callback);
	}
	
	/**
	 * 保存系统用户
	 * @param entity 用户对象
	 * @param roleIds 角色ID集合
	 * @param callback 外部管理用户回调接口
	 * @return
	 */
	public String doSave(UserCoreEntity entity, String[] roleIds, SystemUserCallback callback){
		if(userDao.findUniqueBy("loginId", entity.getLoginId()) != null){
			return MSG_USER_EXIST;
		}
		entity.setCreateTime(NumberGenerator.getSequenceNumber());
		entity.setOrderNum(userDao.getMaxOrderNum(entity.getOrgId()));
		entity.setOrgName(DepartmentUtils.getOrgName(entity.getOrgId()));
		userDao.save(entity);
		
		if(roleIds != null){
			functionDao.insertRoleByUserId(entity.getId(), roleIds);
		}
		
		if(callback != null){
			callback.onCreateNewUser(entity);
		}
		return null;
	}
	
	public void execDelete(String[] ids, SystemUserCallback callback){
		UserCoreEntity user = null;
		for(String id : ids){
			user = userDao.get(id);
			user.setStatus(UserCoreEntity.STATUS_DELETE);
			userDao.updateEntity(user);
			if(callback != null){
				callback.onDeleteUser(id);
			}
		}
	}
	
	public void execEase(String[] ids){
		for(String id : ids){
			userDao.removeById(id);
		}
	}
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param password
	 */
	public void execEditPassword(String userId, String password){
		userDao.updatePassword(userId, password);
	}
	
	public List<RoleEntity> queryAllRoles(){
		return roleDao.getRoleBasicInfoList();
	}
	
	/**
	 * 返回给定用户可展示的角色列表，已经拥有的角色会被选择，供界面调用
	 * @param userId
	 * @return
	 */
	public List<RoleEntity> queryUserEditRoles(String userId){
		List<String> belongRoles = roleDao.getRoleIds(userId);
		List<RoleEntity> allRoles = roleDao.getRoleBasicInfoList();
		if(belongRoles == null || belongRoles.size() == 0){
			return allRoles;
		}
		if(allRoles != null){
			for(RoleEntity r : allRoles){
				if(belongRoles.contains(r.getId()))
					r.setSelected(true);
			}
		}
		return allRoles;
	}
	
	public MapPager queryPagerUserList(int pageIndex, int pageSize){
		return userDao.getPagerUserList(pageIndex, pageSize);
	}
}
