package com.walkersoft.appmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "yl_strategy_group_detail")
public class StrategyGroupDetailEntity {

	int id;
	
	@Column(name = "groupid")
	int groupid;//
	@Column(name = "strategy_id")
	int strategy_id;//
	
	@Column(name = "seq")
	int seq;//
	@Column(name = "isuse")
	int isuse;//
	
	@Id
	@Column(name="id", length=11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getStrategy_id() {
		return strategy_id;
	}
	public void setStrategy_id(int strategy_id) {
		this.strategy_id = strategy_id;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
}
