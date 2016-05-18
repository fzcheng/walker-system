package com.walker.websocket;

import com.walker.app.ApplicationRuntimeException;

public class UserNotFoundException extends ApplicationRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5328459218116083667L;

	public UserNotFoundException(){
		super("not found user!");
	}
	
	public UserNotFoundException(String msg){
		super(msg);
	}
	
	public UserNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
}
