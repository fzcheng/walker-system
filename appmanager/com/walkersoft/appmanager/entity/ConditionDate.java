package com.walkersoft.appmanager.entity;

import java.util.Date;

import com.walkersoft.appmanager.util.DateUtil;

public class ConditionDate {

	int type;//类型 0-每天 1-周几
	String typeValue;//根据type   type为0－忽略此参数 type为1-表示周几 
	String bt;//一天中的开始时间
	String et;//一天中的结束时间
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getEt() {
		return et;
	}
	public void setEt(String et) {
		this.et = et;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	
	/**
	 * 是否包含curtime
	 * @param curtime
	 * @return
	 */
	public boolean isContain(Date curtime) {
		
		int s1 = DateUtil.getDifferTimes_seconds(bt, curtime);
		int s2 = DateUtil.getDifferTimes_seconds(curtime, et);
		
		if(type == 0)
		{
			if(s1 >=0 && s2 >= 0)
			{
				return true;
			}
		}
		else if(type == 1)
		{
			int weekday = DateUtil.getCurrentWeekDay_int();
			
			if(contain(typeValue, weekday) && s1 >=0 && s2 >= 0)
			{
				return true;
			}
		}
		
		return false;
	}
	private boolean contain(String typeValue2, int weekday) {
		String ts[] = typeValue2.split(",");
		for(String t : ts)
		{
			if(Integer.valueOf(t) == weekday)
			{
				return true;
			}
		}
		return false;
	}
}
