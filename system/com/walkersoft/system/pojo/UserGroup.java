package com.walkersoft.system.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String groupName;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	private List<SysOperator> userList;
	
	public List<SysOperator> getUserList() {
		return userList;
	}
	public void addUserObj(SysOperator _user){
		if(userList == null){
			userList = new ArrayList<SysOperator>();
		}
		userList.add(_user);
	}
}
