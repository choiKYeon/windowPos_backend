package com.example.windowPos.global.websocketHandler;

import com.example.windowPos.member.repository.MemberRepository;
import com.example.windowPos.orderManagement.dto.MenuDto;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.dto.OrderUpdateRequest;
import com.example.windowPos.orderManagement.dto.SalesPauseDto;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.entity.SalesPause;
import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
//    웹소켓 메세지를 처리하는 클래스
public class WebSoketMessageHandler extends TextWebSocketHandler {
//    사용자 id와 웹소켓 세션을 매핑하는 hashmap, 사용자와 연결된 세션 추적
    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
    private final OrderManagementRepository orderManagementRepository;
    private final MemberRepository memberRepository;

    @Override
//    연결이 성립되었을 때 호출하는 메서드
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        웹소켓 세션으로부터 사용자 id를 찾아냄
        String userId = searchUserName(session);
//        세션맵에 사용자 id와 웹소켓 세션 추가
        sessionMap.put(userId,session);
    }

    @Override
//    클라이언트에서 메세지를 수신했을 때 호출되는 메서드
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신된 메세지를 가져옴
        String payload = message.getPayload();
//        메세지를 orderUpdateRequest 객체로 변환
        OrderUpdateRequest orderUpdateRequest = parseMessage(payload);
//        주문 상태 업데이트
        updateOrderStatus(orderUpdateRequest);
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

    // JSON형식의 문자열을 orderUpdateRequest 객체로 변환
    private OrderUpdateRequest parseMessage(String payload) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
//            JSON문자열을 orderUpdateRequest 객체로 읽음
            return objectMapper.readValue(payload, OrderUpdateRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 객체를 사용하여 주문 상태를 업데이트 하는 메서드
    private void updateOrderStatus(OrderUpdateRequest request) {
        if (request == null) return;

        // 주문id로 주문을 조회
        OrderManagement order = orderManagementRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
//        주문 상태를 업데이트
        order.setOrderStatus(request.getOrderStatus());
        orderManagementRepository.save(order);

        // 업데이트된 주문 상태를 모든 연결된 클라이언트에게 브로드캐스트
        broadcastOrderUpdate(order);
    }

    // 주문 상태를 브로드캐스트하는 메서드
    private void broadcastOrderUpdate(OrderManagement order) {
        OrderManagementDto orderManagementDTO = convertToDTO(order);
        String orderUpdateMessage = convertToJson(orderManagementDTO);

        for (WebSocketSession session : sessionMap.values()) {
            try {
//                세션을 통해 메세지 전송
                session.sendMessage(new TextMessage(orderUpdateMessage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // OrderManagement 객체를 DTO로 변환하는 메서드
    private OrderManagementDto convertToDTO(OrderManagement order) {
        OrderManagementDto orderManagementDTO = new OrderManagementDto();
        orderManagementDTO.setId(order.getId());
        orderManagementDTO.setOrderTime(order.getOrderTime());
        orderManagementDTO.setRequest(order.getRequest());
        orderManagementDTO.setAddress(order.getAddress());
        orderManagementDTO.setTotalPrice(order.getTotalPrice());
        orderManagementDTO.setOrderNumber(order.getOrderNumber());
        orderManagementDTO.setOrderStatus(order.getOrderStatus());
        orderManagementDTO.setOrderType(order.getOrderType());
        orderManagementDTO.setRejectionReason(order.getRejectionReason());
        orderManagementDTO.setEstimatedCookingTime(order.getEstimatedCookingTime());
        orderManagementDTO.setEstimatedArrivalTime(order.getEstimatedArrivalTime());
        orderManagementDTO.setMenuList(order.getMenuList().stream().map(this::menuDTO).collect(Collectors.toList()));
        orderManagementDTO.setSalesPause(convertToDTO(order.getSalesPause()));
        return orderManagementDTO;
    }

    // Menu 객체를 DTO로 변환하는 메서드
    private MenuDto menuDTO(Menu menu) {
        MenuDto menuDTO = new MenuDto();
        menuDTO.setId(menu.getId());
        menuDTO.setMenuName(menu.getMenuName());
        menuDTO.setPrice(menu.getPrice());
        return menuDTO;
    }

    // SalesPause 객체를 DTO로 변환하는 메서드
    private SalesPauseDto convertToDTO(SalesPause salesPause) {
        SalesPauseDto salesPauseDTO = new SalesPauseDto();
        salesPauseDTO.setId(salesPause.getId());
        salesPauseDTO.setSalesPauseStartTime(salesPause.getSalesPauseStartTime());
        salesPauseDTO.setSalesPauseEndTime(salesPause.getSalesPauseEndTime());
        return salesPauseDTO;
    }

    // DTO 객체를 JSON 문자열로 변환하는 메서드
    private String convertToJson(OrderManagementDto orderManagementDTO) {
        // 실제 구현에서는 Jackson 또는 Gson 등을 사용하여 JSON 변환
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(orderManagementDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
