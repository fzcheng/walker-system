package com.walkersoft.appmanager.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单
 * @author shikeying
 *
 */
@Entity
@Table(name = "yl_daily")
public class DailyEntity {

	int id;
	
	@Column(name = "appid")
	String appid;//应用',
	@Column(name = "market")
	int market;//'渠道',
	@Column(name = "paychannel")
	int paychannel;// '付费渠道 1-ali 2-微信',

	Date date;
	int order_count;
	int order_total_fee;
	
	@Column(name = "create_time")
	Timestamp create_time;// '创建时间',
	
	@Column(name = "last_time")
	Timestamp last_time;// '最后修改时间',

	@Id
	@Column(name="id", length=11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}
	public int getPaychannel() {
		return paychannel;
	}
	public void setPaychannel(int paychannel) {
		this.paychannel = paychannel;
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
}
