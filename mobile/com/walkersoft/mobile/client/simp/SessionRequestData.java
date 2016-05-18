package com.walkersoft.mobile.client.simp;

import com.walkersoft.mobile.client.RequestData;

/**
 * 带sessionID的请求对象默认实现
 * @author shikeying
 * @date 2015-3-5
 *
 */
public abstract class SessionRequestData implements RequestData {

	public static final String SESSION_NAME = "m_session";
	
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public SessionRequestData setSessionId(String sessionId) {
		this.sessionId = sessionId;
		return this;
	}
	
}
