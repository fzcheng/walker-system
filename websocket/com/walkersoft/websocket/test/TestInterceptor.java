package com.walkersoft.websocket.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.walker.websocket.AbstractInterceptor;
import com.walker.websocket.AbstractWebsocketHandler;
import com.walker.websocket.UserNotFoundException;
import com.walker.websocket.support.SimpleHandler;
import com.walkersoft.application.cache.UserCoreCacheProvider;
import com.walkersoft.system.entity.UserCoreEntity;
import com.walkersoft.websocket.pojo.SimpleUserDetails;

@Component
public class TestInterceptor extends AbstractInterceptor {

	@Autowired
	protected UserCoreCacheProvider userCacheProvider;
	
	@Autowired
	private SimpleHandler simpleHandler;
	
	@Override
	protected UserDetails getUserDetails(String userId,
			ServletServerHttpRequest request) throws UserNotFoundException {
		UserCoreEntity user = userCacheProvider.getCacheData(userId);
		if(user == null){
			throw new UserNotFoundException(userId);
		}
		return (UserDetails)new SimpleUserDetails(user);
	}

	@Override
	protected AbstractWebsocketHandler getWebsocketHandler() {
		return simpleHandler;
	}

}
