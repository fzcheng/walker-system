package com.walkersoft.appmanager.entity;

import java.sql.Date;

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
public class AppEntity {

	int appid = 0;
	
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
	
	
	@Id
	@Column(name="appid", length=36)
	public int getAppid() {
		return appid;
	}
	public void setAppid(int id) {
		this.appid = id;
	}
	
}
