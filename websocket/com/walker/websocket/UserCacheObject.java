package com.walker.websocket;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketSession;

import com.walker.infrastructure.utils.Assert;

public class UserCacheObject {

	private UserDetails userDetails;
	private WebSocketSession session;
	
	public UserCacheObject(UserDetails userDetails, WebSocketSession session){
		Assert.notNull(userDetails);
		Assert.notNull(session);
		this.userDetails = userDetails;
		this.session = session;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public WebSocketSession getSession() {
		return session;
	}
	
	/**
	 * 是否该用户连接是否还保持存在，如果是返回<code>true</code>
	 * @return
	 */
	public boolean existSession(){
		if(session != null && session.isOpen()){
			return true;
		} else
			return false;
	}
	
	public String toString(){
		return new StringBuilder().append("[username=").append(userDetails.getUsername())
				.append(", session=").append(session.getId()).toString();
	}
}
