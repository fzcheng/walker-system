package com.walkersoft.appmanager.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 每日统计－应用－日
 * @author shikeying
 *
 */
public class Daily_AppDayEntity {

	String appid;//应用',

	String appname;//应用名',

	Date date;
	int order_count;
	int order_total_fee;
	
	Timestamp create_time;// '创建时间',
	
	Timestamp last_time;// '最后修改时间',

	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getLast_time() {
		return last_time;
	}
	public void setLast_time(Timestamp last_time) {
		this.last_time = last_time;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getOrder_count() {
		return order_count;
	}
	public void setOrder_count(int order_count) {
		this.order_count = order_count;
	}
	public int getOrder_total_fee() {
		return order_total_fee;
	}
	public void setOrder_total_fee(int order_total_fee) {
		this.order_total_fee = order_total_fee;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
}
