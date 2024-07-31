package com.example.windowPos.orderManagement.dto;

import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.entity.SalesPause;

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
        orderManagementDto.setOrderNumber(order.getOrderNumber());
        orderManagementDto.setOrderStatus(order.getOrderStatus());
        orderManagementDto.setOrderType(order.getOrderType());
        orderManagementDto.setRejectionReason(order.getRejectionReason());
        orderManagementDto.setEstimatedCookingTime(order.getEstimatedCookingTime());
        orderManagementDto.setEstimatedArrivalTime(order.getEstimatedArrivalTime());
        orderManagementDto.setMenuList(order.getMenuList().stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
        orderManagementDto.setSalesPause(convertToDto(order.getSalesPause()));
        return orderManagementDto;
    }

    // Menu 객체를 DTO로 변환하는 메서드
    public static MenuDto convertToDto(Menu menu) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(menu.getId());
        menuDto.setMenuName(menu.getMenuName());
        menuDto.setPrice(menu.getPrice());
        return menuDto;
    }

    // SalesPause 객체를 DTO로 변환하는 메서드
    public static SalesPauseDto convertToDto(SalesPause salesPause) {
        SalesPauseDto salesPauseDto = new SalesPauseDto();
        salesPauseDto.setId(salesPause.getId());
        salesPauseDto.setSalesPauseStartTime(salesPause.getSalesPauseStartTime());
        salesPauseDto.setSalesPauseEndTime(salesPause.getSalesPauseEndTime());
        return salesPauseDto;
    }
}
