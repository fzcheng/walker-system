package com.walkersoft.appmanager.entity;

/**
 * 订单状态
 * @author a
 *
 */
public class OrderStatus {

	int value;
	String name;
	
	public OrderStatus(int value, String name)
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
