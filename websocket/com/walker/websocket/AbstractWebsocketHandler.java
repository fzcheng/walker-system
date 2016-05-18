package com.walker.websocket;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.walker.infrastructure.core.filter.ConvertDataException;
import com.walker.infrastructure.core.filter.Convertable;
import com.walker.infrastructure.utils.StringUtils;
import com.walker.websocket.request.RequestToJsonConvertor;
import com.walker.websocket.response.ResponseToJsonConvertor;
import com.walker.websocket.response.SuccessResponse;
import com.walker.websocket.response.WrappedResponse;
import com.walker.websocket.support.ActionContext;

public abstract class AbstractWebsocketHandler implements WebSocketHandler {

	protected transient final Log logger = LogFactory.getLog(getClass());
	
	private final Convertable requestConvertor = new RequestToJsonConvertor();
	private final Convertable responseConvertor= new ResponseToJsonConvertor();
	
	private final SuccessResponse successResponse = new SuccessResponse(responseConvertor);
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
			throws Exception {
		UserDetails userDetails = getUserFromSession(session);
		logger.info("用户断开了连接：" + userDetails.getUsername() + ", statusCode: " + closeStatus.getCode() + ", " + closeStatus.getReason());
		
		// 从缓存中移除用户和连接
		removeUserSession(userDetails.getUsername());
		
		// close，不能在此调用关闭，因为连接已被框架自动关闭了。不能重复调用，报错：
		// java.lang.IllegalStateException: Message will not be sent because the WebSocket session has been closed
//		closeSession(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		UserDetails userDetails = getUserFromSession(session);
		logger.debug("连接已建立，接入用户：" + userDetails.getUsername());
		addUserSession(userDetails, session);
		
		// 发送成功连接消息
		sendSuccessMessage(session, userDetails);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
			throws Exception {
		String requestVal = message.getPayload().toString();
		if(requestVal.equals(StringUtils.EMPTY_STRING)){
			logger.warn("接收到空消息，不处理");
			return;
		} else
			logger.info("用户接收到消息: " + message.getPayload());
		
		// 1：解析用户数据，转换成对象
		Request request = (Request)requestConvertor.convertTo(requestVal);
		if(request == null){
			throw new IllegalArgumentException();
		}
		
		// 2：根据配置的请求路径，匹配对应的业务action
		String url = request.getUrl();
		Action action = ActionContext.getAction(url);
		
		// 3：调用action，并返回业务结果
		WrappedResponse response = new WrappedResponse();
		try{
			action.handleAction(request, response);
			obtainResponse(response, Response.CODE_SUCCESS, Response.SUCCESS);
		} catch(ActionInvokeException ex){
			obtainResponse(response, Response.CODE_SERVER_ERROR, "业务调用异常：" + ex.getMessage());
		}
		
		// 4：把结果转换成json格式返回给客户端
		String result = (String)responseConvertor.convertTo(response);
		logger.info("接收消息，处理完毕，准备发送：" + result);
		
		// 按照请求类型发送响应
		int requestType = request.getType();
		if(requestType == Request.TYPE_SINGLE){
			// 发送给单个用户
			doSendSingle(request, result);
			
		} else if(requestType == Request.TYPE_BROADCAST){
			// 发送广播
			doSendBroadcast(request, result);
		}
	}
	
	private void doSendSingle(Request request, String result) throws IOException{
		String toUser = request.getTo();
		if(StringUtils.isEmpty(toUser)){
			throw new IllegalArgumentException();
		}
		UserCacheObject user = this.getUserSession(toUser);
		if(user == null){
			logger.warn("未找到要发送消息用户，可能用户已经离线: " + toUser);
		} else {
			user.getSession().sendMessage(new TextMessage(result));
		}
		
		// 给当前发送者回执“成功”
		doSendSuccessBack(request.getFrom());
	}
	
	private void doSendBroadcast(Request request, String result) throws IOException{
		String fromUser = request.getFrom();
		UserCacheObject uco = null;
		// 给所有在线用户（除了自己）发送业务结果
		for(Iterator<UserCacheObject> it = getUserSessions(); it.hasNext();){
			uco = it.next();
			if(!uco.getUserDetails().getUsername().equals(fromUser)){
				uco.getSession().sendMessage(new TextMessage(result));
			}
		}
		// 给当前发送者回执“成功”
		doSendSuccessBack(fromUser);
	}
	
	/**
	 * 给给定的用户发送成功消息
	 * @param fromUser
	 * @throws IOException
	 */
	private void doSendSuccessBack(String fromUser) throws IOException{
		UserCacheObject requestUser = this.getUserSession(fromUser);
		if(requestUser == null){
			throw new IllegalArgumentException("未找到请求用户连接，这是不正确的: " + fromUser);
		}
		requestUser.getSession().sendMessage(new TextMessage(successResponse.toString()));
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable cause)
			throws Exception {
		if(cause instanceof ConvertDataException){
			logger.error("解析请求数据时出现异常：" + cause.getMessage());
			return;
		} else if(cause instanceof IllegalArgumentException){
			logger.error("请求或响应的参数不正确，无法继续处理。");
			return;
		}
		
		// 其他错误就关闭连接
		logger.error("数据传输出现异常，连接将被关闭", cause);
		UserDetails userDetails = getUserFromSession(session);
		removeUserSession(userDetails.getUsername());
		closeSession(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	private UserDetails getUserFromSession(WebSocketSession session){
		Object userObj = session.getAttributes().get(Constants.ATTR_USER_OBJECT);
		if(userObj == null){
			throw new IllegalStateException("未设置用户信息。");
		}
		return (UserDetails)userObj;
	}
	
	private void closeSession(WebSocketSession session){
		if(session != null && session.isOpen()){
			try {
				session.close();
			} catch (IOException e) {}
		}
	}
	
	private void obtainResponse(WrappedResponse response, int code, String summary){
		response.setCode(code);
		response.setSummary(summary);
	}

	/**
	 * 添加连接用户到系统缓存中。
	 * @param userDetails
	 * @param session
	 */
	protected abstract void addUserSession(UserDetails userDetails, WebSocketSession session);
	
	/**
	 * 从缓存中移除用户信息和连接对象
	 * @param userId 用户唯一标识
	 */
	protected abstract void removeUserSession(String userId);
	
	protected abstract boolean containUserSession(String userId);
	
	/**
	 * 从系统缓存中返回用户信息，包含用户连接信息
	 * @param userId 用户ID
	 * @return
	 */
	protected abstract UserCacheObject getUserSession(String userId);
	
	/**
	 * 返回当前在线的所有用户连接信息
//	 * @param requestUserId 请求用户，如果设置该值，返回结果就不包含该用户；否则返回全部。
	 * @return
	 */
	protected abstract Iterator<UserCacheObject> getUserSessions();
	
	/**
	 * 发送连接成功消息，给客户端
	 * @param session
	 * @param userDetails
	 * @throws IOException
	 */
	protected void sendSuccessMessage(WebSocketSession session
			, UserDetails userDetails) throws IOException{
		session.sendMessage(new TextMessage("welcome " + userDetails.getUsername()));
	}
}
