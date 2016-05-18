package com.walkersoft.application.security;

import javax.servlet.http.HttpServletRequest;

import com.walker.app.ApplicationCallback;
import com.walkersoft.system.entity.UserCoreEntity;

/**
 * 系统安全回调接口定义.
 * @author shikeying
 * @date 2014-11-27
 *
 */
public interface SystemSecurityCallback extends ApplicationCallback {

	/**
	 * 系统登录之后,回调改接口.
	 * @param loginUser 登陆用户对象
	 * @param request 请求对象
	 */
	public void afterLogin(UserCoreEntity loginUser, HttpServletRequest request);
	
	/**
	 * 注销之前调用该接口
	 * @param userId 用户ID
	 */
	public void beforeLogout(String userId);
}
