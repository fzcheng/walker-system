package com.walkersoft.system.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walker.db.page.support.GenericPager;
import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.NumberGenerator;
import com.walkersoft.system.dao.FunctionDao;
import com.walkersoft.system.dao.RoleDao;
import com.walkersoft.system.entity.RoleEntity;
import com.walkersoft.system.pojo.RoleFunction;

@Service("roleManager")
public class RoleManagerImpl {
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private FunctionDao functionDao;

	/**
	 * 返回所有角色与功能对应关系记录集合
	 * @return
	 */
	public List<RoleFunction> queryRoleFunctionList(){
		return roleDao.getRoleFunctionList();
	}
	
	/**
	 * 返回分页角色列表
	 * @param splitPageInfo
	 * @return
	 * @throws Exception
	 */
	public GenericPager<RoleEntity> querySplitRoleList(){
		return roleDao.getRoleList();
	}
	
	public List<RoleEntity> queryAllRoleList(){
		return roleDao.getAllRoleList();
	}
	
	public List<String> queryRoleUsers(String roleId){
		return roleDao.getRoleUserIds(roleId);
	}
	
	/**
	 * 保存新创建角色
	 * @param roleName
	 * @param summary
	 * @return 存在同名返回1，成功保存返回0
	 * @throws Exception
	 */
	public int execInsertRole(String roleName, String summary){
		return roleDao.insertRole(roleName, summary);
	}
	
	/**
	 * 向特定角色中加入一个用户，此方法由外部模块调用。</p>
	 * 在不存在角色时，会自动添加到系统中。例如：供应商中会自动创建供应商角色了。
	 * @param roleId 角色ID
	 * @param userId 用户ID
	 * @param roleName 角色的名称。
	 */
	public void execJoinUserToRole(String roleId, String userId, String roleName){
		Assert.hasText(roleName);
		RoleEntity entity = roleDao.get(roleId);
		if(entity == null){
			// 创建特定名称的角色
			entity = new RoleEntity();
			entity.setId(roleId);
			entity.setCreateTime(NumberGenerator.getSequenceNumber());
			entity.setName(roleName);
			entity.setSummary("通过其他模块创建的角色");
			roleDao.insertRole(entity);
		}
		List<String> userIds = new ArrayList<String>();
		userIds.add(userId);
		functionDao.insertUserByRoleId(roleId, userIds);
	}
	
	/**
	 * 删除一个角色
	 * @param roleId
	 */
	public String execRemoveRole(String roleId){
		RoleEntity role = roleDao.get(roleId);
		if(role.isSystemRole()){
			return "系统角色不允许删除";
		}
		roleDao.removeById(roleId);
		return null;
	}
}
