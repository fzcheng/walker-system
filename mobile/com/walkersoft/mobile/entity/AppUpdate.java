package com.walkersoft.mobile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="s_mobile_app_update")
public class AppUpdate {

	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "app_id", length=60)
	private String appId;
	
	@Column(name = "summary", length=255)
	private String summary;
	
	@Column(name = "version_code")
	private int versionCode = 1;
	
	@Column(name = "update_force")
	private int updateForce = AppInfo.FORCE_UPDATE_NO;
	
	@Column(name = "file_id", length=36)
	private String fileId;
	
	@Transient
	private String appName;

	public String getAppName() {
		return appName;
	}

	public AppUpdate setAppName(String appName) {
		this.appName = appName;
		return this;
	}

	public long getId() {
		return id;
	}

	public AppUpdate setId(long id) {
		this.id = id;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	public AppUpdate setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getAppId() {
		return appId;
	}

	public AppUpdate setAppId(String appId) {
		this.appId = appId;
		return this;
	}

	public String getSummary() {
		return summary;
	}

	public AppUpdate setSummary(String summary) {
		this.summary = summary;
		return this;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public AppUpdate setVersionCode(int versionCode) {
		this.versionCode = versionCode;
		return this;
	}

	public int getUpdateForce() {
		return updateForce;
	}

	public AppUpdate setUpdateForce(int updateForce) {
		this.updateForce = updateForce;
		return this;
	}

	public String getFileId() {
		return fileId;
	}

	public AppUpdate setFileId(String fileId) {
		this.fileId = fileId;
		return this;
	}
	
}
