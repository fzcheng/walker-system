package com.walkersoft.appmanager.entity;

import com.walkersoft.appmanager.util.ProvinceUtil;


public class ConditionLocation {
	
	int location;

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public String getPname() {
		return ProvinceUtil.getInstance().getProviceName(location);
	}
}
