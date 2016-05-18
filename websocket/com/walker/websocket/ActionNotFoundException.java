package com.walker.websocket;

import com.walker.infrastructure.core.NestedRuntimeException;

public class ActionNotFoundException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 861391136020101702L;

	public ActionNotFoundException(){
		super("not found action!");
	}
	
	public ActionNotFoundException(String msg){
		super(msg);
	}
	
	public ActionNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
}
