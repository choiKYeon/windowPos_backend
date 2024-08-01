package com.example.windowPos.dtoConverter;

import com.example.windowPos.orderManagement.dto.MenuDto;
import com.example.windowPos.orderManagement.dto.MenuOptionDto;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.dto.OrderUpdateRequest;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.MenuOption;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.entity.OrderUpdate;
import com.example.windowPos.setting.dto.OperatePauseDto;
import com.example.windowPos.setting.entity.OperatePause;

import java.util.stream.Collectors;

public class DtoConverter {

    // OrderManagement 객체를 DTO로 변환하는 메서드
    public static OrderManagementDto convertToDto(OrderManagement order) {
        OrderManagementDto orderManagementDto = new OrderManagementDto();
        orderManagementDto.setId(order.getId());
        orderManagementDto.setOrderTime(order.getOrderTime());
        orderManagementDto.setRequest(order.getRequest());
        orderManagementDto.setAddress(order.getAddress());
        orderManagementDto.setTotalPrice(order.getTotalPrice());
        orderManagementDto.setMenuTotalPrice(order.getMenuTotalPrice());
        orderManagementDto.setOrderNumber(order.getOrderNumber());
        orderManagementDto.setDeliveryFee(order.getDeliveryFee());
        orderManagementDto.setSpoonFork(order.getSpoonFork());

//        Enum -> String 타입 변환
        orderManagementDto.setOrderStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null);
        orderManagementDto.setOrderType(order.getOrderType() != null ? order.getOrderType().name() : null);

        orderManagementDto.setMenuList(order.getMenuList().stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

        if (order.getOrderUpdate() != null) {
            orderManagementDto.setOrderUpdateRequest(convertToDto(order.getOrderUpdate()));
        } else {
            orderManagementDto.setOrderUpdateRequest(null);
        }

        return orderManagementDto;
    }

    //    OrderUpdate 객체를 DTO로 변환하는 메서드
    public static OrderUpdateRequest convertToDto(OrderUpdate orderUpdate) {
        OrderUpdateRequest orderUpdateRequest = new OrderUpdateRequest();
        orderUpdateRequest.setId(orderUpdate.getId());
        orderUpdateRequest.setRejectionReason(orderUpdate.getRejectionReason());
        orderUpdateRequest.setEstimatedArrivalTime(orderUpdate.getEstimatedArrivalTime());
        orderUpdateRequest.setEstimatedCookingTime(orderUpdate.getEstimatedCookingTime());
        return orderUpdateRequest;
    }

    // Menu 객체를 DTO로 변환하는 메서드
    public static MenuDto convertToDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setMenuName(menu.getMenuName());
        menuDto.setCount(menu.getCount());
        menuDto.setPrice(menu.getPrice());

        menuDto.setMenuOptionDtoList(menu.getMenuOptions().stream()
                .map(DtoConverter::convertToDto)
                .collect(Collectors.toList()));

        return menuDto;
    }

    //    MenuOption 객체를 DTO로 변환하는 메서드
    public static MenuOptionDto convertToDto(MenuOption menuOption) {
        MenuOptionDto menuOptionDto = new MenuOptionDto();
        menuOptionDto.setId(menuOption.getId());
        menuOptionDto.setOptionName(menuOption.getOptionName());
        menuOptionDto.setOptionPrice(menuOption.getOptionPrice());
        return menuOptionDto;
    }

    // SalesPause 객체를 DTO로 변환하는 메서드
    public static OperatePauseDto convertToDto(OperatePause operatePause) {
        OperatePauseDto operatePauseDto = new OperatePauseDto();
        operatePauseDto.setId(operatePause.getId());
        operatePauseDto.setSalesPauseStartTime(operatePause.getSalesPauseStartTime());
        operatePauseDto.setSalesPauseEndTime(operatePause.getSalesPauseEndTime());
        return operatePauseDto;
    }
}
