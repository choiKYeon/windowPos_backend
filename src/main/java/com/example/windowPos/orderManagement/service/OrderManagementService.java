package com.example.windowPos.orderManagement.service;

import com.example.windowPos.orderManagement.dto.MenuDto;
import com.example.windowPos.orderManagement.dto.MenuOptionDto;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.dto.OrderUpdateRequest;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.MenuOption;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.entity.OrderUpdate;
import com.example.windowPos.orderManagement.orderEnum.OrderStatus;
import com.example.windowPos.orderManagement.orderEnum.OrderType;
import com.example.windowPos.orderManagement.repository.OrderManagementRepository;
import com.example.windowPos.setting.service.SalesPauseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
    private final OrderManagementRepository orderManagementRepository;
    private final SalesPauseService salesPauseService;

    //    메뉴주문을 Dto로 변환해주는 구문
    private Menu convertToEntity(MenuDto menuDto) {
        Menu menu = Menu.builder()
                .menuName(menuDto.getMenuName())
                .price(menuDto.getPrice())
                .count(menuDto.getCount())
                .build();

        if (menuDto.getMenuOptionDtoList() != null) {
            List<MenuOption> menuOptions = menuDto.getMenuOptionDtoList().stream()
                    .map(this::convertToEntityOption)
                    .collect(Collectors.toList());
            menu.setMenuOptions(menuOptions);
        } else {
            menu.setMenuOptions(Collections.emptyList()); // 빈 리스트로 설정
        }
        return menu;
    }

    //    메뉴옵션을 Dto로 변환해주는 구문
    private MenuOption convertToEntityOption(MenuOptionDto menuOptionDto) {
        return MenuOption.builder()
                .optionName(menuOptionDto.getOptionName())
                .optionPrice(menuOptionDto.getOptionPrice())
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

//        주문 총 가격
        Long totalPrice = 0L;

        List<Menu> menus = orderManagementDto.getMenuList().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        OrderManagement order = OrderManagement.builder()
                .orderTime(LocalDateTime.now())
                .request(orderManagementDto.getRequest())
                .address(orderManagementDto.getAddress())
                .menuTotalPrice(0L)
                .totalPrice(0L)
                .deliveryFee(orderManagementDto.getDeliveryFee())
                .spoonFork(orderManagementDto.getSpoonFork())
                .orderNumber(orderNumber)
                .orderStatus(OrderStatus.WAITING)
//                String -> Enum타입 변환
                .orderType(orderManagementDto.getOrderType() != null ? OrderType.valueOf(orderManagementDto.getOrderType()) : null)
                .menuList(menus)
                .build();

        // 각 메뉴와 옵션을 주문에 연결
        for (Menu menu : menus) {
            // 메뉴 옵션 총 가격
            Long menuOptionTotalPrice = 0L;
            // 메뉴와 주문 연결
            menu.setOrderManagement(order);

            // 옵션 가격 총합 계산
            if (menu.getMenuOptions() != null) {
                for (MenuOption menuOption : menu.getMenuOptions()) {
                    menuOptionTotalPrice += menuOption.getOptionPrice();
                    // 옵션과 메뉴 연결
                    menuOption.setMenu(menu);
                }
            }

            // 메뉴 가격과 옵션 가격 총합을 메뉴 수량에 곱하여 총 가격 계산
            Long menuTotalPrice = (menu.getPrice() + menuOptionTotalPrice) * menu.getCount();
            totalPrice += menuTotalPrice;
        }

        order.setMenuTotalPrice(totalPrice);
        order.setTotalPrice(totalPrice + orderManagementDto.getDeliveryFee());

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

        // OrderUpdate 객체가 없는 경우 새로 생성
        if (order.getOrderUpdate() == null) {
            order.setOrderUpdate(new OrderUpdate());
        }

        // 주문 업데이트
        if (request.getRejectionReason() != null) {
            order.getOrderUpdate().setRejectionReason(request.getRejectionReason());
        }
        if (request.getEstimatedCookingTime() != null) {
            order.getOrderUpdate().setEstimatedCookingTime(request.getEstimatedCookingTime());
        }
        if (request.getEstimatedArrivalTime() != null) {
            order.getOrderUpdate().setEstimatedArrivalTime(request.getEstimatedArrivalTime());
        }

        orderManagementRepository.save(order);
    }
}