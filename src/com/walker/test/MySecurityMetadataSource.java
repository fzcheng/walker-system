package com.walker.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;

/**
 * 自定义的"资源权限对应关系"提供者</p>
 * 这里定义了系统所有URL允许访问的角色信息。
 * @author shikeying
 * @date 2013-11-15
 *
 */
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	
	private Log logger = LogFactory.getLog(getClass());

	private ResourceLoadProvider resourceLoaderProvider;
	
	public void setResourceLoaderProvider(
			ResourceLoadProvider resourceLoaderProvider) {
		assert (resourceLoaderProvider != null);
		this.resourceLoaderProvider = resourceLoaderProvider;
		if(resourceMap == null){
			resourceMap = resourceLoaderProvider.loadResource();
		}
	}
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	/* 请求匹配对象放入Map中，避免重复创建 */
	private static Map<String, AntPathRequestMatcher> requestMatchers = new ConcurrentHashMap<String, AntPathRequestMatcher>();
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
//		String url = ((FilterInvocation)object).getRequestUrl();
//		Iterator<String> ite = resourceMap.keySet().iterator();
//		String _defineUrl = null;
//		while(ite.hasNext()){
//			_defineUrl = ite.next().toLowerCase();
//			if(url.indexOf(_defineUrl) >= 0){
//				logger.debug("............> 找到了匹配的资源: " + _defineUrl + ", 请求的资源: " + url);
//				 return resourceMap.get(_defineUrl);
//			}
//		}
		
		Iterator<String> it = resourceMap.keySet().iterator();
		String resURL = null;
		AntPathRequestMatcher requestMatcher = null;
		while(it.hasNext()){
			resURL = it.next();
			requestMatcher = requestMatchers.get(resURL);
			/* 把requestMatcher对象放到Map中，避免重复创建 */
			if(requestMatcher == null){
				requestMatcher = new AntPathRequestMatcher(resURL);
				requestMatchers.put(resURL, requestMatcher);
			}
			if(requestMatcher.matches(((FilterInvocation)object).getRequest())){
				logger.debug("............> 找到了匹配的资源: " + resURL + ", 请求的资源: " + ((FilterInvocation)object).getRequestUrl());
				return resourceMap.get(resURL);
			}
		}
//		return null;
		// 如果根据URL没有找到对应的角色集合，就返回一个空集合；
		// 因为spring security默认对于空集合都放行。
//		logger.debug("---------> 返回了空角色集合。");
		return getEmptyAttributes();
	}
	
	private Collection<ConfigAttribute> getEmptyAttributes(){
		if(emptyAttributes == null){
			emptyAttributes = new ArrayList<ConfigAttribute>();
			emptyAttributes.add(new SecurityConfig("role_empty"));
		}
		return emptyAttributes;
	}
	
	private static Collection<ConfigAttribute> emptyAttributes = null;

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	public synchronized void reloadResource(){
		Map<String, Collection<ConfigAttribute>> map = resourceLoaderProvider.loadResource();
		resourceMap = map;
		requestMatchers.clear();
	}
}
