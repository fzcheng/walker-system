package com.walkersoft.websocket.pojo;

import com.walkersoft.application.security.MyUserDetails;
import com.walkersoft.system.entity.UserCoreEntity;

public class SimpleUserDetails extends MyUserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5773300630805444267L;

	private String userId = null;
	
	public SimpleUserDetails(UserCoreEntity user){
		super(user);
		// 让getUsername()方法返回用户ID，而不是登录名
		userId = user.getId();
	}
	
	@Override
	public String getUsername() {
		return userId;
	}
}
