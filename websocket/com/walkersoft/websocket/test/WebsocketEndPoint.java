package com.walkersoft.websocket.test;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 接收到客户端请求的<code>Handler</code>
 * @author shikeying
 * @date 2014-12-10
 *
 */
public class WebsocketEndPoint extends TextWebSocketHandler {

	@Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");
        session.sendMessage(returnMessage);
    }
}
