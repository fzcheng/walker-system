package com.walkersoft.system.util;

import java.util.Comparator;

import com.walkersoft.system.pojo.FunctionObj;

public class FuncObjComparator implements Comparator<FunctionObj> {

	@Override
	public int compare(FunctionObj o1, FunctionObj o2) {
		// TODO Auto-generated method stub
		if(o1.getOrderNum() < o2.getOrderNum()){
			return 0;
		} else {
			return 1;
		}
	}

}
