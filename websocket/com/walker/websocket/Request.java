package com.walker.websocket;

/**
 * 请求数据对象定义
 * @author shikeying
 * @date 2014-12-17
 *
 */
public interface Request {

	/**
	 * 返回请求的URL，其实就是请求的唯一标识，可以用类似URL方式表示
	 * @return
	 */
	String getUrl();
	
	/**
	 * 返回安全信息，保留字段
	 * @return
	 */
	String getSecurity();
	
	/**
	 * 返回请求类型：
	 * <pre>
	 * 1.点对点类型，消息是给某个特定对象，to字段必须指定用户
	 * 2.广播类型，消息广播给所有在线用户，to字段不必指定
	 * 3.特定消息，保留
	 * </pre>
	 * @return
	 */
	int getType();
	
	/**
	 * 请求消息的用户
	 * @return
	 */
	String getFrom();
	
	/**
	 * 发送的目的用户，如果type = 1，则此字段必须存在值
	 * @return
	 */
	String getTo();
	
	/**
	 * 请求内容正文
	 * @return
	 */
	String getContent();
	
	public static final int TYPE_SINGLE = 1;
	public static final int TYPE_BROADCAST = 2;
	public static final int TYPE_OTHER = 3; // 保留
}
