package com.walkersoft.websocket.test;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket消息拦截器，测试实现。
 * @author shikeying
 * @date 2014-12-10
 *
 */
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	private transient final Log logger = LogFactory.getLog(getClass());
	
	@Override
    public boolean beforeHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        logger.debug("........Before Handshake");
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
 
    @Override
    public void afterHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception ex) {
    	logger.debug(".......After Handshake");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
