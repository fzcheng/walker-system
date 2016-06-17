package com.walkersoft.appmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "yl_strategy_group_detail")
public class StrategyGroupDetailEntity {

	@Id
	@Column(name="id", length=11)
	int id;
	
	//@Column(name = "groupid")
	//int groupid;//
	
	@ManyToOne(targetEntity = StrategyGroupEntity.class)
    @JoinColumn(name = "groupid", referencedColumnName = "group_id")
	StrategyGroupEntity strategyGroup;
	
	//@Column(name = "strategy_id")
	//int strategy_id;//
	@ManyToOne(targetEntity = StrategyEntity.class)
    @JoinColumn(name = "strategy_id", referencedColumnName = "id")
	StrategyEntity strategy;
	
	@Column(name = "seq")
	int seq;//
	@Column(name = "isuse")
	int isuse;//
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public StrategyGroupEntity getStrategyGroup() {
		return strategyGroup;
	}
	public void setStrategyGroup(StrategyGroupEntity strategyGroup) {
		this.strategyGroup = strategyGroup;
	}
	public StrategyEntity getStrategy() {
		return strategy;
	}
	public void setStrategy(StrategyEntity strategy) {
		this.strategy = strategy;
	}
}
