package com.walker;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.walker.infrastructure.cache.support.Cache;

public interface CacheProvider<T> extends InitializingBean, DisposableBean {

	/**
	 * 返回缓存对象
	 * @return
	 */
	Cache getCache();
	
	/**
	 * 返回缓存提供者的名字
	 * @return
	 */
	String getProviderName();
	
	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 因为Cache接口不支持泛型，所以在<code>CacheProvider</code>
	 * 中加入了泛型方法，这些是直接访问缓存数据的方法，这可能有些</br>
	 * 不太合适，因为提供者只需要提供缓存对象就可以了，暂时先不增加</br>
	 * 新接口，直接在此扩充内容，随着演化，这个接口需要调整。
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	/**
	 * 获得一个缓存数据
	 * @param key
	 * @return
	 */
	T getCacheData(String key);
	
	void removeCacheData(String key);
	
	void updateCacheData(String key, T data);
}
