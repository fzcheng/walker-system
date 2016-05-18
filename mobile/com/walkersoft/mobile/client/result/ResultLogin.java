package com.walkersoft.mobile.client.result;

import com.walkersoft.mobile.client.UserDetail;

public class ResultLogin {

	private String session;
	
	private String userId;
	
	private String userName;
	
	private String loginName;
	
	private UserDetail userDetail;

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public ResultLogin setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
		return this;
	}

	public String getSession() {
		return session;
	}

	public ResultLogin setSession(String session) {
		this.session = session;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public ResultLogin setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public ResultLogin setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getLoginName() {
		return loginName;
	}

	public ResultLogin setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}
	
	
}
