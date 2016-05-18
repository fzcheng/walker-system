package com.walker.websocket;

/**
 * 业务处理Action定义。</p>
 * 在Action实现中，应该保持该对象的无状态性，通常会配置到spring环境中，使用单例模式。
 * @author shikeying
 * @date 2014-12-17
 *
 */
public interface Action {

	/**
	 * 处理业务的调用方法
	 * @param request 请求对象
	 * @param response 响应对象
	 * @throws ActionInvokeException 调用失败异常
	 */
	void handleAction(Request request, Response response) throws ActionInvokeException;
	
	/**
	 * 返回该Action能够处理的URL地址
	 * @return
	 */
	String matchUrl();
}
