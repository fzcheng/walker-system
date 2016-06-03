package com.walkersoft.appmanager.entity;

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
@Table(name = "yl_order")
public class OrderEntity {

	int id;
	
	@Column(name = "appid")
	String appid;//应用',
	@Column(name = "appname")
	String appname;//应用',
	@Column(name = "market")
	int market;//'渠道',
	@Column(name = "paychannel")
	int paychannel;// '付费渠道 1-ali 2-微信',
	@Column(name = "userId")
	String userId;//
	@Column(name = "nickname")
	String nickname;//
	@Column(name = "waresId")
	String waresId;//
	@Column(name = "wares")
	String wares;//
	@Column(name = "orderid")
	String orderid;// '平台订单号',
	@Column(name = "cpOrderId")
	String cpOrderId;//;//` varchar(45) DEFAULT NULL COMMENT '付费渠道订单号',
	@Column(name = "payOrderid")
	String payOrderid;
	@Column(name = "ext")
	String ext;// '透传',
	@Column(name = "totalFee")
	int totalFee;// COMMENT '分',
	@Column(name = "status")
	int status;//,
	@Column(name = "isdeal")
	int isdeal;//,
	@Column(name = "retCode")
	String retCode;//
	@Column(name = "retMsg")
	String retMsg;//
	@Column(name = "transfer_status")
	int transfer_status;//,
	@Column(name = "transfer_count")
	int transfer_count;//,
	@Column(name = "transfer_url")
	String transfer_url;// '通知地址',
	@Column(name = "ip")
	String ip;//
	@Column(name = "host")
	String host;//
	@Column(name = "imei")
	String imei;//
	@Column(name = "model")
	String model;
	@Column(name = "mobile")
	String mobile;
	@Column(name = "version")
	String version;
	
	@Column(name = "pay_time")
	Timestamp pay_time;// '创建时间',
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getWaresId() {
		return waresId;
	}
	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}
	public String getWares() {
		return wares;
	}
	public void setWares(String wares) {
		this.wares = wares;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCpOrderId() {
		return cpOrderId;
	}
	public void setCpOrderId(String cpOrderId) {
		this.cpOrderId = cpOrderId;
	}
	public String getPayOrderid() {
		return payOrderid;
	}
	public void setPayOrderid(String payOrderid) {
		this.payOrderid = payOrderid;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public int getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getIsdeal() {
		return isdeal;
	}
	public void setIsdeal(int isdeal) {
		this.isdeal = isdeal;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public int getTransfer_status() {
		return transfer_status;
	}
	public void setTransfer_status(int transfer_status) {
		this.transfer_status = transfer_status;
	}
	public int getTransfer_count() {
		return transfer_count;
	}
	public void setTransfer_count(int transfer_count) {
		this.transfer_count = transfer_count;
	}
	public String getTransfer_url() {
		return transfer_url;
	}
	public void setTransfer_url(String transfer_url) {
		this.transfer_url = transfer_url;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
	public Timestamp getPay_time() {
		return pay_time;
	}
	public void setPay_time(Timestamp pay_time) {
		this.pay_time = pay_time;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
