package com.walkersoft.appmanager.entity;

import java.util.ArrayList;
import java.util.List;

public class StrategyCondition {

	int type;
	String value;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	//时间条件
	public List<ConditionDate> getConditionDate() {
		List<ConditionDate> l = new ArrayList<ConditionDate> ();
		String v = getValue();
		String vs[] = v.split("|");
		for(String t : vs)
		{
			String ts[] = t.split(",");
			
			int type = Integer.valueOf(ts[0]);
			String typeValue = ts[1];
			String bt = ts[2];
			String et = ts[3];
			
			ConditionDate cd = new ConditionDate();
			cd.setBt(bt);
			cd.setEt(et);
			cd.setType(type);
			cd.setTypeValue(typeValue);
			l.add(cd);
		}
		
		return l;
	}
	//区域条件
	public List<ConditionLocation> getConditionLocation() {
		List<ConditionLocation> l = new ArrayList<ConditionLocation> ();
		String v = getValue();
		String vs[] = v.split("|");
		for(String t : vs)
		{
			ConditionLocation cd = new ConditionLocation();
			cd.setLocation(Integer.valueOf(t));
			l.add(cd);
		}
		
		return l;
	}
}
