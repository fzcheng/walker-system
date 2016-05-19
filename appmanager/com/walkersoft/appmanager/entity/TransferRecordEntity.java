package com.walkersoft.appmanager.entity;

import java.sql.Timestamp;

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
@Table(name = "yl_app")
public class TransferRecordEntity {

	int id;
	
	String appid;
	/*0-ipos 1-szf*/
	protected java.lang.Integer ordertype = null;
	/**/
	protected java.lang.String orderid = null;
	/**/
	protected java.lang.String tid = null;
	int paychannel;
	/**/
	protected java.lang.String url = null;
	/**/
	protected java.lang.String data = null;
	/**/
	protected java.lang.String response = null;

	@Column(name = "create_time")
	Timestamp create_time;// '创建时间',
	
	@Column(name = "last_time")
	Timestamp last_time;// '最后修改时间',
	
	public TransferRecordEntity() { }
	
	
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


	public java.lang.Integer getOrdertype() {
		return ordertype;
	}


	public void setOrdertype(java.lang.Integer ordertype) {
		this.ordertype = ordertype;
	}


	public java.lang.String getOrderid() {
		return orderid;
	}


	public void setOrderid(java.lang.String orderid) {
		this.orderid = orderid;
	}


	public java.lang.String getTid() {
		return tid;
	}


	public void setTid(java.lang.String tid) {
		this.tid = tid;
	}


	public int getPaychannel() {
		return paychannel;
	}


	public void setPaychannel(int paychannel) {
		this.paychannel = paychannel;
	}


	public java.lang.String getUrl() {
		return url;
	}


	public void setUrl(java.lang.String url) {
		this.url = url;
	}


	public java.lang.String getData() {
		return data;
	}


	public void setData(java.lang.String data) {
		this.data = data;
	}


	public java.lang.String getResponse() {
		return response;
	}


	public void setResponse(java.lang.String response) {
		this.response = response;
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
	
}
