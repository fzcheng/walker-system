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
@Table(name = "yl_app")
public class AppEntity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id", length=11)
	int id;
	
	@Column(name="appid", length=36)
	String appid;
	
	@Column(name = "appcode")
	String appcode;// 'app码',
	
	@Column(name = "appname")
	String appname;// '应用名称',
	
	@Column(name = "package_name")
	String package_name;// '包名',
	
	@Column(name = "companyid")
	int companyid;// '公司id',
	
	@Column(name = "create_time")
	long create_time;// '创建时间',
	
	@Column(name = "last_time")
	long last_time;// '最后修改时间',

	@Column(name = "wx_appid")
	String wx_appid;
	
	@Column(name = "wx_parternerKey")
	String wx_parternerKey;
	
	@Column(name = "wx_mch_id")
	String wx_mch_id;
	
	@Column(name = "notify_url")
	String notify_url;
	
	public String getAppcode() {
		return appcode;
	}
	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid) {
		this.companyid = companyid;
	}
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
	public String getWx_appid() {
		return wx_appid;
	}
	public void setWx_appid(String wx_appid) {
		this.wx_appid = wx_appid;
	}
	public String getWx_parternerKey() {
		return wx_parternerKey;
	}
	public void setWx_parternerKey(String wx_parternerKey) {
		this.wx_parternerKey = wx_parternerKey;
	}
	public String getWx_mch_id() {
		return wx_mch_id;
	}
	public void setWx_mch_id(String wx_mch_id) {
		this.wx_mch_id = wx_mch_id;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
}
