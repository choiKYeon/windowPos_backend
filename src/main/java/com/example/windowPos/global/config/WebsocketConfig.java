package com.example.windowPos.global.config;

import com.example.windowPos.global.websocketHandler.WebSoketMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebsocketConfig implements WebSocketConfigurer {

    private final WebSoketMessageHandler webSoketMessageHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSoketMessageHandler, "/ws/orders").setAllowedOrigins("*");
    }

}
