package com.walkersoft.deploy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.walker.infrastructure.utils.DateUtils;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 授权管理对象定义
 * @author shikeying
 * @date 2014-11-4
 *
 */
@Entity
@Table(name = "sm_accredit")
public class AccreditEntity {

	@Id
	@Column(name="id")
	private long id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name="org_name", length=50)
	private String orgName;
	
	@Column(name="project_name", length=100)
	private String projectName;
	
	@Column(name="auth_id", length=20)
	private String authId;
	
	@Column(name="jar_name", length=50)
	private String jarName;
	
	@Column(name = "auth_type")
	private int authType = 2;
	
	@Column(name="mac_address", length=20)
	private String macAddress;
	
	@Column(name = "start_time")
	private long startTime = 0;
	
	@Column(name = "end_time")
	private long endTime = 0;
	
	@Column(name="file_id", length=36)
	private String fileId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public int getAuthType() {
		return authType;
	}

	public void setAuthType(int authType) {
		this.authType = authType;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public String toString(){
		return new StringBuilder().append("[orgName=").append(orgName)
				.append(", authId=").append(authId)
				.append(", authType=").append(authType)
				.append(", macAddress=").append(macAddress)
				.append(", start=").append(startTime)
				.append(", end=").append(endTime)
				.append("]").toString();
	}
	
	public void setStartTimeShow(String date){
		if(StringUtils.isNotEmpty(date)){
			this.startTime = DateUtils.getDateLongEarly(date);
		}
	}
	
	public void setEndTimeShow(String date){
		if(StringUtils.isNotEmpty(date)){
			this.endTime = DateUtils.getDateLongLate(date);
		}
	}
}
