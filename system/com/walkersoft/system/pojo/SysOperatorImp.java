package com.walkersoft.system.pojo;

public class SysOperatorImp extends Object implements SysOperator {

	private String userId;
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	private String userName;
	private String group;
	
	@Override
	public boolean enabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getGroup() {
		// TODO Auto-generated method stub
		return group;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}

}
