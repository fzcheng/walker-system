package com.walkersoft.websocket.test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {

	public static final String WEBSOCKET_USERNAME = "user";
	
	private transient final Log logger = LogFactory.getLog(getClass());
	
	private static final ArrayList<WebSocketSession> users;
	
	static {
        users = new ArrayList<WebSocketSession>();
    }
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1)
			throws Exception {
		logger.debug("websocket connection closed......");
		System.out.println("客户端关闭了连接...");
        users.remove(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		logger.debug("connect to the websocket success......");
        users.add(session);
        String userName = (String) session.getAttributes().get(WEBSOCKET_USERNAME);
        if(userName != null){
            //查询未读消息
//            int count = webSocketService.getUnReadNews((String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME));
            session.sendMessage(new TextMessage("有新的消息呦"));
        }
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
			throws Exception {
		logger.debug("收到客户端的消息: " + message.getPayload());
		System.out.println("收到客户端的消息: " + message.getPayload());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable throwable)
			throws Exception {
		if(session.isOpen()){
            session.close();
        }
        logger.debug("websocket connection closed......");
        logger.error("传输数据出现异常", throwable);
        users.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get(WEBSOCKET_USERNAME).equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    
}
