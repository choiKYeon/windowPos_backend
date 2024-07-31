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
//    웹소켓 설정 커스텀마이징
public class WebsocketConfig implements WebSocketConfigurer {

//    웹소켓 메세지를 처리
    private final WebSoketMessageHandler webSoketMessageHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        /orders 엔드포인트에 핸들러로 등록 후 CORS 요청 허용
        registry.addHandler(webSoketMessageHandler, "/ws/orders").setAllowedOrigins("*");
    }

}
