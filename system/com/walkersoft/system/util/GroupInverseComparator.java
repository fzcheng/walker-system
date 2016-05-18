package com.walkersoft.system.util;

import java.util.Comparator;

import com.walkersoft.system.pojo.FunctionObj;

public class GroupInverseComparator implements Comparator<FunctionObj> {

	@Override
	public int compare(FunctionObj o1, FunctionObj o2) {
//		if(o1.getOrderNum() < o2.getOrderNum()){
//			return 0;
//		} else {
//			return 1;
//		}
		return (o1.getOrderNum() - o2.getOrderNum());
	}

}
