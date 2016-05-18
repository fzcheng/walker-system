package com.walker.websocket;

/**
 * 响应对象定义。
 * @author shikeying
 * @date 2014-12-17
 *
 */
public interface Response {

	/**
	 * 返回响应代码
	 * @return
	 */
	int getCode();
	
	/**
	 * 返回响应描述，例如异常描述
	 * @return
	 */
	String getSummary();
	
	/**
	 * 返回内容
	 * @return
	 */
	String getContent();
	
	void setContent(String content);
	
	/**
	 * 响应代码标志
	 */
	public static final int CODE_SUCCESS = 200;
	public static final int CODE_ACTION_NOT_FOUND = 404;
	public static final int CODE_SERVER_ERROR = 500;
	public static final int CODE_UNKNOWN = 999;
	
	public static final String SUCCESS = "success";
}
