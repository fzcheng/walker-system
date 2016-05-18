package com.walkersoft.websocket.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.walker.websocket.AbstractInterceptor;
import com.walker.websocket.AbstractWebsocketConfig;
import com.walker.websocket.AbstractWebsocketHandler;
import com.walker.websocket.support.SimpleHandler;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class TestConfig extends AbstractWebsocketConfig {

	@Override
	protected AbstractInterceptor createInterceptor() {
		System.out.println("====================> " + testInterceptor);
		return testInterceptor;
	}

	@Override
	protected AbstractWebsocketHandler createWebsocketHandler() {
		return simpleHandler;
	}

	@Autowired
	private TestInterceptor testInterceptor;
	
	@Autowired
	private SimpleHandler simpleHandler;
}
