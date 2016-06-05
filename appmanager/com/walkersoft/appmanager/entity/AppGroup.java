package com.walkersoft.appmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	private List<AppEntity> appList;
	
	public List<AppEntity> getAppList() {
		return appList;
	}
	public void addAppObj(AppEntity _user){
		if(appList == null){
			appList = new ArrayList<AppEntity>();
		}
		appList.add(_user);
	}
}
