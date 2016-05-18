package com.walkersoft.system.pojo;

import java.io.Serializable;

/**
 * 系统配置参数对象定义
 * @author MikeShi
 *
 */
public class PreferObj implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getSxh() {
		return sxh;
	}
	public void setSxh(long sxh) {
		this.sxh = sxh;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	private long sxh;
	private String name;
	private int dataType = 0;
	private String dataValue;
	
	private String defaultValue;
	private String groupName;
}
