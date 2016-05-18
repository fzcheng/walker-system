package com.walker.websocket;

import com.walker.infrastructure.core.NestedRuntimeException;

public class ActionInvokeException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7297485855392325139L;

	public ActionInvokeException(){
		super("action invoke failed.");
	}
	
	public ActionInvokeException(String msg){
		super(msg);
	}
	
	public ActionInvokeException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
