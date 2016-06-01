package com.walkersoft.system.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单组对象
 * @author MikeShi
 *
 */
public class AppGroup implements Serializable, Comparable<AppGroup>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appid;
	private String appname;
	private List<String> options = new ArrayList<String>();
	
	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	@Override
	public int compareTo(AppGroup o) {
		return appid.equals(o.appid)?0:1;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public void addOption(String _p) {
		if(options == null)
			options = new ArrayList<String>();
		options.add(_p);
	}
}
