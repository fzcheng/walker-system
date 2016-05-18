package com.walker.websocket.support;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketSession;

import com.walker.infrastructure.utils.Assert;
import com.walker.websocket.AbstractWebsocketHandler;
import com.walker.websocket.UserCacheObject;

/**
 * 默认的websocket消息处理器实现。
 * @author shikeying
 * @date 2014-12-16
 *
 */
public class SimpleHandler extends AbstractWebsocketHandler {

	/* 缓存的用户、连接信息 */
	private final ConcurrentHashMap<String, UserCacheObject> cacheData = new ConcurrentHashMap<String, UserCacheObject>(8 * 8);
	
	@Override
	protected void addUserSession(UserDetails userDetails,
			WebSocketSession session) {
		Assert.notNull(userDetails);
		String userId = userDetails.getUsername();
		cacheData.put(userId, new UserCacheObject(userDetails, session));
		logger.debug("加入了一个用户连接: " + userId);
		logger.info("当前缓存中有用户：" + cacheData.size());
	}

	@Override
	protected void removeUserSession(String userId) {
		cacheData.remove(userId);
		logger.info("当前缓存中有用户：" + cacheData.size());
	}
	
	@Override
	protected boolean containUserSession(String userId){
		UserCacheObject co = cacheData.get(userId);
		if(co == null)
			return false;
		if(co.existSession()){
			return false;
		}
		return true;
	}

	@Override
	protected UserCacheObject getUserSession(String userId) {
		Assert.hasText(userId);
		return cacheData.get(userId);
	}

	@Override
	protected Iterator<UserCacheObject> getUserSessions() {
		return cacheData.values().iterator();
	}

}
