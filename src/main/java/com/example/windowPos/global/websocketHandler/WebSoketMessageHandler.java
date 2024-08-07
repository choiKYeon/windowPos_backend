package com.example.windowPos.global.websocketHandler;

import com.example.windowPos.orderManagement.dto.*;
import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import com.example.windowPos.orderManagement.service.OrderManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
//    웹소켓 메세지를 처리하는 클래스
public class WebSoketMessageHandler extends TextWebSocketHandler {
    //    사용자 id와 웹소켓 세션을 매핑하는 hashmap, 사용자와 연결된 세션 추적
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

    @Override
//    연결이 성립되었을 때 호출하는 메서드
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        웹소켓 세션으로부터 사용자 id를 찾아냄
        String userId = searchUserName(session);
//        세션맵에 사용자 id와 웹소켓 세션 추가
        sessionMap.put(userId, session);
    }

    @Override
//    연결이 닫혔을 때 호출되는 메서드
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션에서 사용자 id 호출
        String userId = searchUserName(session);
//        세션맵에서 사용자 id 제거
        sessionMap.remove(userId);
    }

    // 웹소켓 세션의 URI를 파싱하여 사용자 id를 추출하는 메서드
    public String searchUserName(WebSocketSession session) {
//        URL파싱
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(session.getUri().toString()).build();
//        쿼리 매개변수에 uid값을 추출, 웹소켓에 연결할 때 "uid" 파라미터를 붙여서 보내야 searchUserName 메소드가 작동
        return uriComponents.getQueryParams().getFirst("uid");
    }

    // 주문 상태를 브로드캐스트하는 메서드
    public void broadcastOrderUpdate(OrderManagementDto orderManagementDto) {
        String orderUpdateMessage = convertToJson(orderManagementDto);
        System.out.println("잘 될거야 :" + orderUpdateMessage);
        for (WebSocketSession session : sessionMap.values()) {
            try {
//                세션을 통해 메세지 전송
                System.out.println("진짜 잘 보내질거야 :" + session.getId());
                session.sendMessage(new TextMessage(orderUpdateMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // DTO 객체를 JSON 문자열로 변환하는 메서드
    private String convertToJson(OrderManagementDto orderManagementDTO) {
        // 실제 구현에서는 Jackson 또는 Gson 등을 사용하여 JSON 변환
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(orderManagementDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
