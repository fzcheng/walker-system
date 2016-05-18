package com.walkersoft.system.util;

import java.util.Comparator;

import com.walkersoft.system.pojo.FunctionGroup;

public class FuncGrpComparator implements Comparator<FunctionGroup> {

	@Override
	public int compare(FunctionGroup o1, FunctionGroup o2) {
		// TODO Auto-generated method stub
		if(o1.getOrderNum() < o2.getOrderNum()){
			return 0;
		} else {
			return 1;
		}
	}

}
