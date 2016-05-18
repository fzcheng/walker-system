package com.walkersoft.mobile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="s_mobile_login")
public class Login {

	@Id
	@Column(name = "id", length=50)
	private String id;
	
	@Column(name = "create_time")
	private long createTime;
	
	@Column(name = "update_time")
	private long updateTime;
	
	@Column(name = "user_id", length=36)
	private String userId;
	
	@Column(name = "login_id", length=50)
	private String loginId;
	
	@Column(name = "equipment_name", length=50)
	private String equipmentName;
	
	@Column(name = "equipment_os", length=20)
	private String equipmentOs;
	
	@Column(name = "login_count")
	private int loginCount = 1;
	
	@Column(name = "summary", length=50)
	private String summary;

	public String getId() {
		return id;
	}

	public Login setId(String id) {
		this.id = id;
		return this;
	}

	public long getCreateTime() {
		return createTime;
	}

	public Login setCreateTime(long createTime) {
		this.createTime = createTime;
		return this;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public Login setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public Login setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getLoginId() {
		return loginId;
	}

	public Login setLoginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public Login setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
		return this;
	}

	public String getEquipmentOs() {
		return equipmentOs;
	}

	public Login setEquipmentOs(String equipmentOs) {
		this.equipmentOs = equipmentOs;
		return this;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public Login setLoginCount(int loginCount) {
		this.loginCount = loginCount;
		return this;
	}

	public String getSummary() {
		return summary;
	}

	public Login setSummary(String summary) {
		this.summary = summary;
		return this;
	}
	
	public void addOneTime(){
		this.loginCount += 1;
	}
	
	public String toString(){
		return new StringBuilder().append("[id=").append(id)
				.append(", loginId=").append(loginId)
				.append(", equip_name=").append(equipmentName)
				.append("]").toString();
	}
}
