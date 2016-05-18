package com.walkersoft.application.util;

import com.walker.infrastructure.cache.SimpleCacheManager;
import com.walkersoft.application.cache.FunctionCacheProvider;
import com.walkersoft.system.pojo.FunctionObj;

public abstract class FunctionUtils {

	private FunctionUtils(){}
	
	private static final FunctionCacheProvider functionCache = (FunctionCacheProvider)SimpleCacheManager.getCacheProvider(FunctionObj.class);
	
	private static final String SYSTEM_PARENT_VALUE = "-1";
	
	/**
	 * 返回菜单名称
	 * @param id 菜单ID
	 * @return
	 */
	public static final String getFunctionName(String id){
		if(id.equals(SYSTEM_PARENT_VALUE)){
			return SYSTEM_PARENT_VALUE;
		}
		FunctionObj f = functionCache.getCacheData(id);
		if(f == null){
			return id;
		}
		return f.getName();
	}
}
