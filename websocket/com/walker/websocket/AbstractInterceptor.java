package com.walker.websocket;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.walker.infrastructure.utils.StringUtils;

public abstract class AbstractInterceptor implements HandshakeInterceptor {

	protected transient final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
			WebSocketHandler handler, Exception exception) {

	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			logger.debug("使用了websocket方式连接，握手。");
			ServletServerHttpRequest req = (ServletServerHttpRequest)request;
			String userId = req.getServletRequest().getParameter(Constants.ATTR_USER_ID);
            System.out.println("userId = " + userId);
            if(StringUtils.isEmpty(userId)){
            	System.out.println("缺少用户认证信息：" + Constants.ATTR_USER_ID);
            	return false;
            }
            
            // 从业务中找出用户身份
            UserDetails userDetails = null;
            try{
            	userDetails = getUserDetails(userId, req);
            } catch(UserNotFoundException ex){
            	throw new Exception("未找到认证用户", ex);
            }
            if(userDetails == null){
            	throw new Exception("未找到认证用户:" + userId);
            }
            
            // 是否已经存在了该用户的缓存连接数据
            AbstractWebsocketHandler customHandler = getWebsocketHandler();
            if(customHandler == null){
            	throw new Error("未配置websocketHandler自定义对象");
            }
//            boolean exist = ((AbstractWebsocketHandler)handler).containUserSession(userId);
            boolean exist = customHandler.containUserSession(userId);
            if(exist){
            	logger.warn("已经存在了该用户连接，不能重复连接：" + userId);
            	return false;
            }
            
            // 把链接用户对象放到属性中，供Handler的afterConnectionEstablished方法是用
            attributes.put(Constants.ATTR_USER_OBJECT, userDetails);
            logger.info("用户拦截完成，userId = " + userId);
            
		} else {
			throw new IllegalArgumentException("未支持的连接协议");
		}
		return true;
	}

	/**
	 * 返回连接用户对象，该方法由业务系统自己实现。</p>
	 * 系统会调用该方法获取用户，并传递给<code>Handler</code>。
	 * @param userId 用户ID
	 * @param request 请求对象
	 * @return
	 * @throws UserNotFoundException
	 */
	protected abstract UserDetails getUserDetails(String userId
			, ServletServerHttpRequest request) throws UserNotFoundException;
	
	/**
	 * 返回用户自定义的消息处理<code>WebsocketHandler</code>
	 * @return
	 */
	protected abstract AbstractWebsocketHandler getWebsocketHandler();
}
