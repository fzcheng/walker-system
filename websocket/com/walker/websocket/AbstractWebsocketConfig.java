package com.walker.websocket;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 基础的配置websocket的对象，子类来负责具体的拦截、handler的创建，同时可以在用户环境中注入bean。
 * @author shikeying
 * @date 2014-12-16
 *
 */
public abstract class AbstractWebsocketConfig extends WebMvcConfigurerAdapter 
implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(systemWebSocketHandler(),"/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor());
		AbstractInterceptor interceptor = createInterceptor();
		if(interceptor == null){
			throw new IllegalStateException("not found interceptor.");
		}
		AbstractWebsocketHandler handler = createWebsocketHandler();
		if(handler == null){
			throw new IllegalStateException("not found handler.");
		}
		registry.addHandler(handler,"/websocket/index.do").addInterceptors(interceptor);
        registry.addHandler(handler, "/websocket/sockjs.do").addInterceptors(interceptor)
                .withSockJS();
	}
	
	/**
	 * 创建自定义拦截器
	 * @return
	 */
	protected abstract AbstractInterceptor createInterceptor();
	
	/**
	 * 创建自定义handler
	 * @return
	 */
	protected abstract AbstractWebsocketHandler createWebsocketHandler();
}
