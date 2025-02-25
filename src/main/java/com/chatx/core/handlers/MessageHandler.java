package com.chatx.core.handlers;

import com.chatx.core.entity.Message;
import com.chatx.core.repository.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageHandler extends TextWebSocketHandler {

    private final MessageRepository messageRepository;
    private final ObjectMapper objectMapper;
    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>();

    @Autowired
    public MessageHandler(MessageRepository messageRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session, "Anonymous");
        System.out.println("New connection: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Disconnected: " + session.getId());
    }

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String payload = message.getPayload();
            System.out.println("Received message: " + payload);
            ModelMessage incoming = objectMapper.readValue(payload, ModelMessage.class);

            if (incoming.getName() != null && !incoming.getName().isEmpty()) {
                sessions.put(session, incoming.getName());
            }

            if (incoming.getMessage() == null || incoming.getMessage().trim().isEmpty()) {
                System.out.println("Message content is null or empty, ignoring message.");
                return;
            }

            // Prepare message to save
            Message dbMessage = new Message();
            dbMessage.setUsername(sessions.get(session));
            dbMessage.setContent(incoming.getMessage());
            dbMessage.setRoute(session.getUri().getPath());
            dbMessage.setCreatedAt(LocalDateTime.now());

            // Save to database
            messageRepository.save(dbMessage);

            // Prepare response
            ModelMessage outgoing = new ModelMessage();
            outgoing.setName(sessions.get(session));
            outgoing.setMessage(incoming.getMessage());

            // Broadcast to all connected clients
            broadcastMessage(outgoing);

        } catch (IOException e) {
            System.err.println("Error processing message: " + e.getMessage());
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    private void broadcastMessage(ModelMessage message) throws IOException {
        String json = objectMapper.writeValueAsString(message);
        for (WebSocketSession session : sessions.keySet()) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(json));
            }
        }
    }
}