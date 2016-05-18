package com.walkersoft.mobile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.walker.infrastructure.utils.Assert;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.mobile.util.JsonUtils;
import com.walkersoft.system.SystemAction;

/**
 * 抽象的手机终端业务回调对象，手机接口需要继承该对象实现具体业务。
 * @author shikeying
 * @date 2014-12-10
 *
 * extends WebContextAction
 */
public abstract class AbstractCallback extends SystemAction implements Callback {

	protected final transient Log logger = LogFactory.getLog(getClass());
	
	private Map<String, Integer> methods = new HashMap<String, Integer>(8);
	
	@Override
	public void invokeCallback(String userId, HttpServletResponse response) throws NoMethodException, IOException {
		String method = this.getParameter(PARAM_METHOD);
		String session = this.getParameter(PARAM_SESSION);
		logger.debug("............ method = " + method + ", session = " + session);
		if(StringUtils.isEmpty(method) 
				|| StringUtils.isEmpty(session)){
			this.ajaxOutPutJson(JsonUtils.getErrorJson("未找到调用方法或者缺少session信息"));
			return;
//			throw new InvokeException("未找到调用方法或者缺少session信息。");
		}
		
		if(!containMethod(method)){
			throw new NoMethodException();
		}
		
//		// 验证用户
//		UserCoreEntity user = getUserInfo(session);
//		if(user == null){
//			this.ajaxOutPutJson(JsonUtils.getErrorJson("未登陆或者不存在用户信息"));
//			return;
//		}
		
		// 调用业务
		try {
			JSONObject result = doInvokeData(method, userId);
			this.ajaxOutPutJson(JsonUtils.getSuccessJson(result));
		} catch (Exception e) {
			logger.error("调用业务发生异常：" + e.getMessage(), e);
			this.ajaxOutPutJson(JsonUtils.getErrorJson("业务调用异常：" + e.getMessage()));
		}
	}
	
	@Override
	public void invokeCallback(JSONObject parameters, String userId
			, HttpServletResponse response) throws NoMethodException, IOException{
		String method = this.getParameter(PARAM_METHOD);
		if(StringUtils.isEmpty(method)){
			this.ajaxOutPutJson(JsonUtils.getErrorJson("未找到调用方法(method)"));
			return;
		}
		if(!containMethod(method)){
			throw new NoMethodException();
		}
		
		// 调用业务
		try {
			JSONObject result = doInvokeData(method, userId, parameters);
			this.ajaxOutPutJson(JsonUtils.getSuccessJson(result));
		} catch (Exception e) {
			logger.error("调用业务发生异常：" + e.getMessage(), e);
			this.ajaxOutPutJson(JsonUtils.getErrorJson("业务调用异常：" + e.getMessage()));
		}
	}

	@Override
	public boolean containMethod(String requestMethod) {
		Assert.hasText(requestMethod);
		if(methods.get(requestMethod.toLowerCase()) != null){
			return true;
		}
		return false;
	}

	/**
	 * 返回该回掉对象能支持的方法名称集合，之外的方法访问将被拒绝。
	 * @return
	 */
	protected abstract List<String> getMethodList();
	
	/**
	 * 处理业务逻辑的抽象方法，该方法需要业务在子类中实现。
	 * @param method 手机请求方法名称
	 * @param userId 请求的用户ID
	 * @param request 请求对象
	 * @return 返回业务响应数据，JSON对象
	 * @throws Exception
	 */
	protected abstract JSONObject doInvokeData(String method
			, String userId) throws Exception;
	
	/**
	 * 处理业务逻辑的抽象方法，该方法需要业务在子类中实现。</p>
	 * 此方法专为请求中使用json数据参数的情况设计使用。
	 * @param method
	 * @param userId
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	protected abstract JSONObject doInvokeData(String method
			, String userId, JSONObject parameters) throws Exception;
	
//	/**
//	 * 返回请求用户信息
//	 * @param session 给手机端的m_session
//	 * @return
//	 */
//	protected abstract UserCoreEntity getUserInfo(String session);

	@Override
	public void afterPropertiesSet() throws Exception {
		List<String> methodList = getMethodList();
		if(!StringUtils.isEmptyList(methodList)){
			for(String m : methodList){
				methods.put(m.toLowerCase(), 1);
			}
		}
	}
	
}
