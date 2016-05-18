package com.walkersoft.application;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.system.pojo.FunctionObj;

/**
 * 菜单匹配的映射处理实现</br>
 * 从注册的注解中找到匹配菜单的所有URL，同时把菜单中多余的连接去掉，防止出现：显示菜单但点击链接报错的情况。
 * @author shikeying
 *
 */
public class MenuMatchRequestHandlerMapping extends
		RequestMappingHandlerMapping {
	
	private static final String VIEW_SUFFIX = ".do";
	
	/* key = 菜单项URL, value = 菜单对象， 注意：每个菜单的URL绝对不能重复! */
	private Map<String, FunctionObj> objMap = new HashMap<String, FunctionObj>();
	
	public void setFunctionCacheProvider(FunctionCacheProvider functionCacheProvider) {
		assert (functionCacheProvider != null);
		List<FunctionObj> functionObjs = functionCacheProvider.getAllFunctions();
		for(FunctionObj fo : functionObjs){
			objMap.put(fo.getUrl(), fo);
		}
		logger.debug("系统数据库共有功能: " + objMap.size() + " 个");
	}

	/**
	 * Register a handler method and its unique mapping.
	 * 
	 * @param handler the bean name of the handler or the handler instance
	 * @param method the method to register
	 * @param mapping the mapping conditions associated with the handler method
	 * @throws IllegalStateException if another method was already registered 
	 * under the same mapping
	 */
	@Override
	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
//		HandlerMethod handlerMethod;
//		if (handler instanceof String) {
//			String beanName = (String) handler;
//			handlerMethod = new HandlerMethod(beanName, getApplicationContext(), method);
//		}
//		else {
//			handlerMethod = new HandlerMethod(handler, method);
//		}
//		
//		HandlerMethod oldHandlerMethod = handlerMethods.get(mapping);
//		if (oldHandlerMethod != null && !oldHandlerMethod.equals(handlerMethod)) {
//			throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + handlerMethod.getBean()
//					+ "' bean method \n" + handlerMethod + "\nto " + mapping + ": There is already '"
//					+ oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
//		}
//		
//		handlerMethods.put(mapping, handlerMethod);
//		if (logger.isInfoEnabled()) {
//			logger.info("Mapped \"" + mapping + "\" onto " + handlerMethod);
//		}
//		
//		Set<String> patterns = getMappingPathPatterns(mapping);
//		for (String pattern : patterns) {
//			if (!getPathMatcher().isPattern(pattern)) {
//				urlMap.add(pattern, mapping);
//			}
//		}
		super.registerHandlerMethod(handler, method, mapping);
		if(mapping != null){
			PatternsRequestCondition prc = mapping.getPatternsCondition();
			if(prc != null && prc.getPatterns() != null){
				String key = prc.getPatterns().iterator().next() + VIEW_SUFFIX;
				FunctionObj fo = objMap.get(key);
				if(fo != null){
					// 注解中的URL匹配了菜单项
					fo.setFinishScanAnnotation(true);
//					logger.info(".............> 匹配了一个URL: " + key);
				}
			}
		}
	}
	
	/**
	 * Invoked when a matching mapping is found.
	 * </br>暂未起作用
	 * @param mapping the matching mapping 
	 * @param lookupPath mapping lookup path within the current servlet mapping
	 * @param request the current request
	 */
	@Override
	protected void handleMatch(RequestMappingInfo mapping, String lookupPath, HttpServletRequest request) {
		super.handleMatch(mapping, lookupPath, request);
//		logger.info("-----------> 获得了一个URL: " + mapping.getPatternsCondition().getPatterns());
	}
	
	/**
	 * Invoked when no matching mapping is not found.
	 * </br>暂未起作用
	 * @param mappings all registered mappings
	 * @param lookupPath mapping lookup path within the current servlet mapping
	 * @param request the current request
	 * @throws ServletException in case of errors
	 */
	@Override
	protected HandlerMethod handleNoMatch(Set<RequestMappingInfo> mappings, String lookupPath, HttpServletRequest request)
			throws ServletException {
//		logger.info("..........handleNoMatch: " + mappings);
		return super.handleNoMatch(mappings, lookupPath, request);
	}
}
