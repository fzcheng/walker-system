package com.walkersoft.websocket.test;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.walker.infrastructure.utils.StringUtils;

/**
 * websocket握手连接拦截器，系统默认实现。
 * @author shikeying
 * @date 2014-12-12
 *
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1,
			WebSocketHandler arg2, Exception arg3) {

	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler arg2,
			Map<String, Object> attributes) throws Exception {
		System.out.println("======================== 拦截 websocket: ");
		if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//            HttpSession session = servletRequest.getServletRequest().getSession(false);
//            if (session != null) {
//                //使用userName区分WebSocketHandler，以便定向发送消息
//                String userName = (String) session.getAttribute("user_id");
//                attributes.put(SystemWebSocketHandler.WEBSOCKET_USERNAME,userName);
//            }
            String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
            System.out.println("userId = " + userId);
            if(StringUtils.isEmpty(userId)){
            	System.out.println("缺少用户认证信息：userId");
            	return false;
            }
            
//            if(userId.equals("0")){
//            	ServletServerHttpResponse res = (ServletServerHttpResponse)response;
//            	res.getServletResponse().getWriter().print("用户认证错误：" + userId);
//            	return false;
//            }
            
            attributes.put(SystemWebSocketHandler.WEBSOCKET_USERNAME,userId);
        }
        return true;
	}

}
