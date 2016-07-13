package com.walkersoft.appmanager.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 应用
 * @author shikeying
 *
 */
@Entity
@Table(name = "yl_app_market")
public class AppMarketEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false)
	Integer id;
	
	//@Column(name="appid", length=36)
	//String appid;
	
	@Column(name = "market")
	int market;// 'market',
	
	//@Column(name = "strategy_groupid")
	@ManyToOne(targetEntity = StrategyGroupEntity.class)
    @JoinColumn(name = "strategy_groupid", referencedColumnName = "group_id")
	StrategyGroupEntity strategyGroup;
	
	@Column(name = "seq")
	int seq;// 'market',
	
	@Column(name = "remark")
	String remark;// '备注',
	
	@Column(name = "create_time")
	Timestamp create_time;// '创建时间',
	
	@Column(name = "last_time")
	Timestamp last_time;// '最后修改时间',

	@ManyToOne(targetEntity = AppEntity.class)
    @JoinColumn(name = "appid", referencedColumnName = "appid")
	AppEntity app;
	
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public AppEntity getApp() {
		return app;
	}
	public void setApp(AppEntity app) {
		this.app = app;
	}
	public StrategyGroupEntity getStrategyGroup() {
		return strategyGroup;
	}
	public void setStrategyGroup(StrategyGroupEntity strategyGroup) {
		this.strategyGroup = strategyGroup;
	}
}
