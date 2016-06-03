package com.walkersoft.appmanager.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 每日统计－应用－日－渠道－市场
 * @author shikeying
 *
 */
@Entity
@Table(name = "yl_device_data")
public class DeviceEntity {

	int id;
	
	String imei;
	@Column(name = "appid")
	String appid;//应用',
	Date date;
	int suc_count;
	int total_count;
	int suc_total_fee;
	int total_fee;
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public int getSuc_count() {
		return suc_count;
	}
	public void setSuc_count(int suc_count) {
		this.suc_count = suc_count;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getSuc_total_fee() {
		return suc_total_fee;
	}
	public void setSuc_total_fee(int suc_total_fee) {
		this.suc_total_fee = suc_total_fee;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
}
