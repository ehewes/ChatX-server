package com.chatx.core.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MessageHandler extends TextWebSocketHandler {

    List<WebSocketSession> webSocketSessions
            = Collections.synchronizedList(new ArrayList<>());
    @Override
    public void
    afterConnectionEstablished(WebSocketSession session)
            throws Exception
    {
        super.afterConnectionEstablished(session);
        System.out.println(session.getId() + " Connected");
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus status)throws Exception
    {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId()
                + " Disconnected");
        webSocketSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session,
                              WebSocketMessage<?> message)
            throws Exception
    {

        super.handleMessage(session, message);

        for (WebSocketSession webSocketSession :
                webSocketSessions) {
            if (session == webSocketSession)
                continue;

            webSocketSession.sendMessage(message);
        }
    }
}
