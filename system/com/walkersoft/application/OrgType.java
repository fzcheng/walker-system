package com.walkersoft.application;

public enum OrgType {

	/**
	 * 机构类型：单位
	 */
	Org{
		public int getTypeValue(){
			return TYPE_ORG;
		}
	},
	
	/**
	 * 机构类型：部门
	 */
	Dept{
		public int getTypeValue(){
			return TYPE_DEPT;
		}
	},
	
	/**
	 * 机构类型：所有，部门 + 单位
	 */
	All{
		public int getTypeValue(){
			return TYPE_ALL;
		}
	};
	
	static final int TYPE_ORG = 0;
	static final int TYPE_DEPT = 1;
	static final int TYPE_ALL = 2;
	
	/**
	 * 返回机构类型值
	 * @return
	 */
	public int getTypeValue(){
		throw new AbstractMethodError();
	}
}
