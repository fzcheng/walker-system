package com.walkersoft.appmanager.entity;

/**
 * 状态
 * @author a
 *
 */
public class ApposStatus {

	int value;
	String name;
	
	public ApposStatus(int value, String name)
	{
		this.value = value;
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
