package com.chatx.core.config;


import com.chatx.core.handlers.MessageHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageHandler messageHandler;

    @Autowired
    public WebSocketConfig(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageHandler, "/chatx")
                .setAllowedOrigins("*");
    }
}
