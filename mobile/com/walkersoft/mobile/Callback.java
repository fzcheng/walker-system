package com.walkersoft.mobile;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.InitializingBean;

/**
 * 业务回掉接口定义
 * @author shikeying
 *
 */
public interface Callback extends InitializingBean {

	public static final String PARAM_SESSION = "m_session";
	public static final String PARAM_METHOD = "method";
	public static final String PARAM_CODE = "code";
	public static final String PARAM_MESSAGE = "message";
	
	public static final String PARAM_DATA = "data";
	
	/**
	 * APP传递过来的JSON字符串参数，给定特殊的名字（key）。
	 */
	public static final String PARAM_DATAS = "datas";
	
	public static final String MESSAGE_SUCCESS = "success";
	
	public static final String METHOD_LOGIN = "login";
	
	/**
	 * 提交业务请求
	 * @param request
	 * @param response
	 */
	void invokeCallback(String userId
			, HttpServletResponse response) throws NoMethodException, IOException;
	
	/**
	 * 客户端以JSON方式提交请求参数，来调用业务的模式。</p>
	 * 在此模式下，不能直接从<code>HttpServletRequest</code>对象中获取参数，只能由系统统一解析提供，<br>
	 * 这种模式通常会把请求参数解密处理，然后对返回的响应数据同时进行加密处理。
	 * @param parameters json参数
	 * @param userId 用户ID
	 * @param response
	 * @throws NoMethodException
	 * @throws IOException
	 */
	void invokeCallback(JSONObject parameters, String userId
			, HttpServletResponse response) throws NoMethodException, IOException;
	
	/**
	 * 请求的方法是否在该回掉类中定义，如果存在返回<code>true</code>
	 * @param requestMethod 请求的方法
	 * @return
	 */
	boolean containMethod(String requestMethod);
}
