package com.walkersoft.appmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 应用
 * @author shikeying
 *
 */
@Entity
@Table(name = "yl_app_market")
public class AppMarketEntity {

	int id;
	
	@Column(name="appid", length=36)
	String appid;
	
	@Column(name = "market")
	int market;// 'app码',
	
	@Column(name = "sdkid")
	int sdkid;// '应用名称',
	
	@Column(name = "remark")
	String remark;// '包名',
	
	@Column(name = "create_time")
	long create_time;// '创建时间',
	
	@Column(name = "last_time")
	long last_time;// '最后修改时间',

	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public long getLast_time() {
		return last_time;
	}
	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}
	
	
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
	public void setAppid(String id) {
		this.appid = id;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}
	public int getSdkid() {
		return sdkid;
	}
	public void setSdkid(int sdkid) {
		this.sdkid = sdkid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
