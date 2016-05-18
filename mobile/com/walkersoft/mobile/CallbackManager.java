package com.walkersoft.mobile;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 手机回掉对象管理器。
 * @author shikeying
 * @date 2015-1-14
 *
 */
public abstract class CallbackManager {

	private static final transient Log logger = LogFactory.getLog(CallbackManager.class);
	
	private static final Map<Class<?>, Callback> cacheProvidersMap = new ConcurrentHashMap<Class<?>, Callback>();

	/**
	 * 向管理器中添加（注册）一个手机回掉对象
	 * @param cacheProvider
	 */
	public static final void addCallback(Callback callback){
		assert (callback != null);
		if(!cacheProvidersMap.containsKey(callback.getClass())){
			cacheProvidersMap.put(callback.getClass(), callback);
		} else {
			logger.debug("已经注册了对象，不能重复注册: " + callback);
		}
	}
	
	/**
	 * 返回回掉对象
	 * @param clazz 对应实体类型，如：<code>BusinessCallback.class</code>
	 * @return
	 */
	public static final Callback getCallback(Class<?> clazz){
		Callback cp = cacheProvidersMap.get(clazz);
		if(cp == null)
			throw new NullPointerException("not found callback: " + clazz.getName());
		return cp;
	}
	
	/**
	 * 返回系统定义的所有回掉对象集合。如果不存返回<code>null</code>
	 * @return
	 */
	public static final Collection<Callback> getCallbackList(){
		if(cacheProvidersMap.size() == 0)
			return null;
		return cacheProvidersMap.values();
	}
}
