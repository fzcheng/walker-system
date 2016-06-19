package com.walkersoft.appmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "yl_strategy")
public class StrategyEntity {

	int id;
	
	@Column(name = "strategy_name")
	String strategy_name;//策略名
	@Column(name = "type1")
	int type1;//
	@Column(name = "value1")
	String value1;//
	
	@Column(name = "type2")
	int type2;//
	@Column(name = "value2")
	String value2;//
	
	@Column(name = "type3")
	int type3;//
	@Column(name = "value3")
	String value3;//
	
	int sdkid;
	int alitype;
	int wxtype;
	
	@Id
	@Column(name="id", length=11)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStrategy_name() {
		return strategy_name;
	}
	public void setStrategy_name(String strategy_name) {
		this.strategy_name = strategy_name;
	}
	public int getType1() {
		return type1;
	}
	public void setType1(int type1) {
		this.type1 = type1;
	}
	public String getValue1() {
		return value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	public int getType2() {
		return type2;
	}
	public void setType2(int type2) {
		this.type2 = type2;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public int getType3() {
		return type3;
	}
	public void setType3(int type3) {
		this.type3 = type3;
	}
	public String getValue3() {
		return value3;
	}
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	public int getSdkid() {
		return sdkid;
	}
	public void setSdkid(int sdkid) {
		this.sdkid = sdkid;
	}
	public int getAlitype() {
		return alitype;
	}
	public void setAlitype(int alitype) {
		this.alitype = alitype;
	}
	public int getWxtype() {
		return wxtype;
	}
	public void setWxtype(int wxtype) {
		this.wxtype = wxtype;
	}
	
	
	//条件－－时间段
	public void setTimeDetail(String[] s) {
	}
	public String[] getTimeDetail() {
		return new String[]{"1", "2", "3"};
	}
	
	//条件－－区域－－省
	public void setProvinceDetail(String[] s) {
	}
	public String[] getProvinceDetail() {
		return new String[]{"1", "2", "3"};
	}
}
