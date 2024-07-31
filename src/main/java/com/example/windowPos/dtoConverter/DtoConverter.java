package com.example.windowPos.dtoConverter;

import com.example.windowPos.orderManagement.dto.MenuDto;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.setting.dto.SalesPauseDto;
import com.example.windowPos.setting.entity.SalesPause;

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
//        Enum -> String 타입 변환
        orderManagementDto.setOrderStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null);
        orderManagementDto.setOrderType(order.getOrderType() != null ? order.getOrderType().name() : null);
        orderManagementDto.setEstimatedCookingTime(order.getEstimatedCookingTime());
        orderManagementDto.setEstimatedArrivalTime(order.getEstimatedArrivalTime());
        orderManagementDto.setMenuList(order.getMenuList().stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));

        // Null 체크 후 변환
        if (order.getSalesPause() != null) {
            orderManagementDto.setSalesPause(DtoConverter.convertToDto(order.getSalesPause()));
        } else {
            orderManagementDto.setSalesPause(null);
        }
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
