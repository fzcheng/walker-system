package com.walkersoft.application;

import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walker.infrastructure.utils.StringUtils;
import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.application.log.MyLogDetail;
import com.walkersoft.application.log.MyLogDetail.LogType;
import com.walkersoft.system.pojo.FunctionObj;

public abstract class MyApplicationContext {

	public static final String STYLE_DEFAULT = "simple";
	public static final String STYLE_SESSION_NAME = "style";
	
	public static String contextPath = null;
	
	/**
	 * web应用上下文路径，即：项目根路径，为ztree自定义icon定义的
	 * @return
	 */
	public static String getContextPath() {
		return contextPath;
	}

	public static void setContextPath(String contextPath) {
		if(MyApplicationContext.contextPath != null){
			return;
		}
		MyApplicationContext.contextPath = contextPath;
	}

	private static final FunctionCacheProvider functionCache = 
			(FunctionCacheProvider)SimpleCacheManager.getCacheProvider(FunctionObj.class);
	
	/**
	 * 返回系统功能（菜单）缓存对象</p>
	 * 该方法不能在系统初始化中调用，如果业务需要初始化调用，请使用依赖注入方式，
	 * 并且加上'depend-on'属性。
	 * @return
	 */
	public static final FunctionCacheProvider getFunctionCache(){
		return functionCache;
	}
	
	public static final String getUserLoginId(){
		return MyApplicationConfig.getCurrentUser().getLoginId();
	}
	
	/**
	 * 写入系统日志
	 * @param content
	 */
	public static final void systemLog(String content){
		MyApplicationConfig.getLogEngine().put(new MyLogDetail().setContent(content));
	}
	public static final void systemLog(String content, LogType type){
		MyApplicationConfig.getLogEngine().put(new MyLogDetail()
		.setContent(content).setType(type));
	}
	public static final void systemLog(String content, LogType type, String loginId){
		assert (StringUtils.isNotEmpty(loginId));
		MyApplicationConfig.getLogEngine().put(new MyLogDetail(loginId)
		.setContent(content).setType(type));
	}
	
	/**
	 * 手动刷新日志引擎，把里面缓存的日志立即持久化。
	 */
	public synchronized static final void flushSystemLog(){
		MyApplicationConfig.getLogEngine().flush();
	}
}
