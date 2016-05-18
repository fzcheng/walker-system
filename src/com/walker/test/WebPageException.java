package com.walker.test;

import com.walker.infrastructure.core.NestedRuntimeException;

/**
 * 页面异常定义，此异常只能应用在UI界面中</p>
 * 通常在控制层中会抛出此异常表示业务数据或参数异常，在系统的出口拦截器中会捕获此异常</br>
 * 并处理页面响应，可以提示给用户。
 * @author shikeying
 *
 */
public class WebPageException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3986922626624027611L;

	private static final String DEFAULT_MSG = "page error.";
	
	private String destinationUrl;
	
	/**
	 * 返回出现异常时的页面地址
	 * @return
	 */
	public String getDestinationUrl() {
		return destinationUrl;
	}

	public WebPageException(){
		super(DEFAULT_MSG);
	}
	
	public WebPageException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	/**
	 * 页面异常构造方法
	 * @param msg
	 * @param cause
	 * @param destinationUrl 出现异常的原始页面地址
	 */
	public WebPageException(String msg, Throwable cause, String destinationUrl){
		super(msg, cause);
		this.destinationUrl = destinationUrl;
	}
	
	public WebPageException(String msg, String destinationUrl){
		super(msg);
		this.destinationUrl = destinationUrl;
	}
}
