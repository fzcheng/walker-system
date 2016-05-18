package com.walkersoft.application;

public enum UserType {

	SuperVisor{
		public int getTypeValue(){
			return TYPE_SUPER;
		}
	},
	Administrator{
		public int getTypeValue(){
			return TYPE_ADMIN;
		}
	},
	User{
		public int getTypeValue(){
			return TYPE_NORMAL;
		}
	};
	
	static final int TYPE_SUPER = 0;
	static final int TYPE_ADMIN = 1;
	static final int TYPE_NORMAL = 2;
	
	/**
	 * 返回用户类型值
	 * @return
	 */
	public int getTypeValue(){
		throw new AbstractMethodError();
	}
}
