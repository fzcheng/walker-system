package com.walker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.walker.infrastructure.cache.RigidMemoryCache;
import com.walker.infrastructure.cache.support.Cache;
import com.walker.infrastructure.utils.StringUtils;

/**
 * 抽象的缓存对象提供者。</p>
 * 每个应用缓存对象都需要实现一个此对象，如：用户缓存、参数缓存等。
 * @author shikeying
 *
 * @param <T>
 */
public abstract class AbstractCacheProvider<T> implements CacheProvider<T> {

	private static Log logger = LogFactory.getLog(AbstractCacheProvider.class);
	
	private Cache userCache = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		String name = getProviderName();
		if(StringUtils.isEmpty(name))
			name = String.valueOf(System.currentTimeMillis());
		if(userCache == null){
			userCache = new RigidMemoryCache(name);
			int count = loadDataToCache(userCache);
			logger.info("cache '" + name + "' loaded size of datas: " + count);
		}
	}
	
	/**
	 * 由应用系统加载持久化数据到缓存对象中，如下：
	 * <pre>
	 * List<T> list = getResultFromDB();
	 * for(T data : list){
	 * 	cache.put(data.getId(), data);
	 * }
	 * </pre>
	 * @param cache
	 * @return
	 */
	protected abstract int loadDataToCache(Cache cache);

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		if(userCache != null)
			userCache.clear();
	}

	@Override
	public Cache getCache() {
		// TODO Auto-generated method stub
		if(userCache == null)
			throw new NullPointerException("not found cache: " + getProviderName());
		return userCache;
	}

//	@Override
//	public String getProviderName() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public T getCacheData(String key) {
		// TODO Auto-generated method stub
		assert (StringUtils.isNotEmpty(key));
		return (T)userCache.get(key);
	}

	@Override
	public void removeCacheData(String key) {
		// TODO Auto-generated method stub
		userCache.remove(key);
	}

	@Override
	public void updateCacheData(String key, T data) {
		// TODO Auto-generated method stub
		assert (StringUtils.isNotEmpty(key));
		assert (data != null);
		userCache.replace(key, data);
	}

	public String toString(){
		return new StringBuilder().append("cacheName = ").append(getProviderName())
				.append(", cache = ").append(userCache).toString();
	}
}
