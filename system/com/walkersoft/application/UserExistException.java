package com.walkersoft.application;

import com.walker.web.WebPageException;

public class UserExistException extends WebPageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994027138566103213L;

	public UserExistException(){
		super("user is exist", (Throwable)null);
	}
	
	public UserExistException(String msg){
		super(msg, (Throwable)null);
	}
}
