package com.walkersoft.mobile;

import com.walker.infrastructure.core.NestedCheckedException;

public class InvokeException extends NestedCheckedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 56709488285475272L;

	public InvokeException(){
		super("mobile invoke exception!");
	}
	
	public InvokeException(String msg){
		super(msg);
	}
	
	public InvokeException(String msg, Throwable cause){
		super(msg, cause);
	}
}
