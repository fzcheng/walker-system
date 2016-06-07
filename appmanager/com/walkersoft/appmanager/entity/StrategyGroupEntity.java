package com.walkersoft.appmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "yl_strategy_group")
public class StrategyGroupEntity {

	int group_id;
	
	@Column(name = "group_name")
	String group_name;//策略名
	@Column(name = "remark")
	String remark;//
	
	@Id
	@Column(name="group_id", length=11)
	public int getGroup_id() {
		return group_id;
	}
	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
