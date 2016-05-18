package com.walkersoft.mobile;

/**
 * Callback中未找到匹配的方法异常
 * @author shikeying
 *
 */
public class NoMethodException extends InvokeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3932506160477131287L;

	public NoMethodException(){
		super("no method found.");
	}
	
	public NoMethodException(String msg){
		super(msg);
	}
	
	public NoMethodException(String msg, Throwable cause){
		super(msg, cause);
	}
}
