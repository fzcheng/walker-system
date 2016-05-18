package com.walkersoft.system.callback;

import com.walker.app.ApplicationCallback;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 定义系统用户管理的回调接口，例如：可以在CMS中实现一个回调，来同步系统用户的管理。
 * @author shikeying
 *
 */
public interface SystemUserCallback extends ApplicationCallback {

	/**
	 * 当系统创建新用户时，回调该接口
	 * @param user
	 */
	public void onCreateNewUser(UserCoreEntity user);
	
	/**
	 * 当系统更新用户时，回调该接口
	 * @param user
	 */
	public void onUpdateUser(UserCoreEntity user);
	
	/**
	 * 当系统删除用户时，回调该接口
	 * @param userId
	 */
	public void onDeleteUser(String userId);
}
