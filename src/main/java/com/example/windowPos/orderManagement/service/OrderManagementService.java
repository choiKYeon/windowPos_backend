package com.example.windowPos.orderManagement.service;

import com.example.windowPos.dtoConverter.DtoConverter;
import com.example.windowPos.orderManagement.dto.MenuDto;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.dto.OrderUpdateRequest;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.orderEnum.OrderStatus;
import com.example.windowPos.orderManagement.orderEnum.OrderType;
import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import com.example.windowPos.setting.dto.SalesPauseDto;
import com.example.windowPos.setting.entity.SalesPause;
import com.example.windowPos.setting.repository.SalesPauseRepository;
import com.example.windowPos.setting.service.SalesPauseService;
import com.example.windowPos.setting.settingEnum.SalesStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final OrderManagementRepository orderManagementRepository;
    private final SalesPauseService salesPauseService;

//    메뉴주문을 Dto로 변환해주는 구문
    private Menu convertToEntity(MenuDto menuDto) {
        return Menu.builder()
                .menuName(menuDto.getMenuName())
                .price(menuDto.getPrice())
                .build();
    }

//    주문번호 찾아서 1씩 더하는 구문
    private Long getNextOrderNumber() {
        Long maxOrderNumber = orderManagementRepository.findMaxOrderNumber();
        return (maxOrderNumber != null) ? maxOrderNumber + 1 : 1;
    }

//    주문 생성하는 구문
    @Transactional
    public OrderManagement createOrder(OrderManagementDto orderManagementDto) {

        if (salesPauseService.isSalesPaused()) {
            throw new IllegalStateException("현재 영업이 일시 정지 상태입니다. 주문을 생성할 수 없습니다.");
        }

        Long orderNumber = getNextOrderNumber();

        List<Menu> menus = orderManagementDto.getMenuList().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        OrderManagement order = OrderManagement.builder()
                .orderTime(LocalDateTime.now())
                .request(orderManagementDto.getRequest())
                .address(orderManagementDto.getAddress())
                .totalPrice(orderManagementDto.getTotalPrice())
                .orderNumber(orderNumber)
                .orderStatus(OrderStatus.WAITING)
//                String -> Enum타입 변환
                .orderType(orderManagementDto.getOrderType() != null ? OrderType.valueOf(orderManagementDto.getOrderType()) : null)
                .menuList(menus)
                .build();

        menus.forEach(menu -> menu.setOrderManagement(order));
        order.setMenuList(menus);

        return orderManagementRepository.save(order);
    }

    // 객체를 사용하여 주문 상태를 업데이트 하는 메서드
    @Transactional
    public void updateOrderStatus(OrderUpdateRequest request) {
        if (request == null) return;

        if (salesPauseService.isSalesPaused()) {
            throw new IllegalStateException("현재 영업이 일시 정지 상태입니다. 주문을 생성할 수 없습니다.");
        }

        // 주문id로 주문을 조회
        OrderManagement order = orderManagementRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("주문 못 찾음"));

        OrderStatus newStatus = OrderStatus.valueOf(request.getOrderStatus());

        switch (newStatus) {
            case IN_PROGRESS:
                order.acceptOrder();
                break;
            case COMPLETED:
                // 주문이 진행 중일 때만 완료가 가능
                order.completeOrder();
                break;
            case REJECTED:
                // 주문이 대기 중일 때만 거절이 가능
                order.rejectOrder();
                break;
            case CANCELLED:
                // 주문이 진행 중일 때만 취소가 가능
                order.cancelOrder();
                break;
            default:
                throw new IllegalArgumentException("상태 업로드 실패");
        }
        orderManagementRepository.save(order);
    }
}