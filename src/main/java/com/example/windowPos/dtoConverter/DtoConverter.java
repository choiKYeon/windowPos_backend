package com.example.windowPos.dtoConverter;

import com.example.windowPos.orderManagement.dto.MenuDto;
import com.example.windowPos.orderManagement.dto.MenuOptionDto;
import com.example.windowPos.orderManagement.dto.OrderManagementDto;
import com.example.windowPos.orderManagement.dto.OrderUpdateRequest;
import com.example.windowPos.orderManagement.entity.Menu;
import com.example.windowPos.orderManagement.entity.MenuOption;
import com.example.windowPos.orderManagement.entity.OrderManagement;
import com.example.windowPos.orderManagement.entity.OrderUpdate;
import com.example.windowPos.setting.dto.*;
import com.example.windowPos.setting.entity.*;

import java.time.Duration;
import java.time.LocalDate;
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

    //    Setting 객체를 DTO로 변환하는 메서드
    public static SettingDto convertToDto(Setting setting) {
        SettingDto settingDto = new SettingDto();
        settingDto.setId(setting.getId());
        settingDto.setOperatePauseDto(convertToDto(setting.getOperatePause()));
        settingDto.setClosedDaysDto(convertToDto(setting.getClosedDays()));
        settingDto.setOperateTimeDto(convertToDto(setting.getOperateTime()));
        settingDto.setEstimatedCookingTimeDto(convertToDto(setting.getEstimatedCookingTime()));
        settingDto.setEstimatedArrivalTimeDto(convertToDto(setting.getEstimatedArrivalTime()));
        settingDto.setBreakTimeDto(convertToDto(setting.getBreakTime()));
        settingDto.setMemberId(setting.getMember().getId());
        return settingDto;
    }

    //    BreakTime 객체를 DTO로 변환하는 메서드
    public static BreakTimeDto convertToDto(BreakTime breakTime) {
        BreakTimeDto breakTimeDto = new BreakTimeDto();
        breakTimeDto.setId(breakTime.getId());
        breakTimeDto.setBreakTimeStart(breakTime.getBreakTimeStart());
        breakTimeDto.setBreakTimeEnd(breakTime.getBreakTimeEnd());
        return breakTimeDto;
    }

    //    EstimatedArrivalTime 객체를 DTO로 변환하는 메서드
    public static EstimatedArrivalTimeDto convertToDto(EstimatedArrivalTime estimatedArrivalTime) {
        EstimatedArrivalTimeDto estimatedArrivalTimeDto = new EstimatedArrivalTimeDto();
        estimatedArrivalTimeDto.setId(estimatedArrivalTime.getId());
        estimatedArrivalTimeDto.setEstimatedArrivalTimeControl(estimatedArrivalTime.getEstimatedArrivalTimeControl());
        estimatedArrivalTimeDto.setEstimatedArrivalTime(estimatedArrivalTime.getEstimatedArrivalTime());
        return estimatedArrivalTimeDto;
    }

    //    EstimatedCookingTime 객체를 DTO로 변환하는 메서드
    public static EstimatedCookingTimeDto convertToDto(EstimatedCookingTime estimatedCookingTime) {
        EstimatedCookingTimeDto estimatedCookingTimeDto = new EstimatedCookingTimeDto();
        estimatedCookingTimeDto.setId(estimatedCookingTime.getId());
        estimatedCookingTimeDto.setEstimatedCookingTimeControl(estimatedCookingTime.getEstimatedCookingTimeControl());
        estimatedCookingTimeDto.setEstimatedCookingTime(estimatedCookingTime.getEstimatedCookingTime());
        return estimatedCookingTimeDto;
    }

    //    OperateTime 객체를 DTO로 변환하는 메서드
    public static OperateTimeDto convertToDto(OperateTime operateTime) {
        OperateTimeDto operateTimeDto = new OperateTimeDto();
        operateTimeDto.setId(operateTime.getId());
        operateTimeDto.setWeekdayAllDay(operateTime.getWeekdayAllDay());
        operateTimeDto.setWeekdayStartTime(operateTime.getWeekdayStartTime());
        operateTimeDto.setWeekdayEndTime(operateTime.getWeekdayEndTime());
        operateTimeDto.setSaturdayEndTime(operateTime.getSaturdayEndTime());
        operateTimeDto.setSundayAllDay(operateTime.getSundayAllDay());
        operateTimeDto.setSundayEndTime(operateTime.getSundayEndTime());
        operateTimeDto.setSundayStartTime(operateTime.getSundayStartTime());
        return operateTimeDto;
    }

    //    ClosedDays 객체를 DTO로 변환하는 메서드
    public static ClosedDaysDto convertToDto(ClosedDays closedDays) {
        ClosedDaysDto closedDaysDto = new ClosedDaysDto();
        closedDaysDto.setId(closedDays.getId());
        closedDaysDto.setRegularHolidayList(closedDays.getRegularHolidayList().stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
        closedDaysDto.setTemporaryHolidayDates(closedDays.getTemporaryHolidayList().stream().map(DtoConverter::convertToDto).collect(Collectors.toList()));
        return closedDaysDto;
    }

    //    TemporaryHoliday 객체를 DTO로 변환하는 메서드
    public static TemporaryHolidayDto convertToDto(TemporaryHoliday temporaryHoliday) {
        TemporaryHolidayDto temporaryHolidayDto = new TemporaryHolidayDto();
        temporaryHolidayDto.setId(temporaryHoliday.getId());
        temporaryHolidayDto.setTemporaryHolidayStartDate(temporaryHoliday.getTemporaryHolidayStartDate());
        temporaryHolidayDto.setTemporaryHolidayEndDate(temporaryHoliday.getTemporaryHolidayEndDate());
        return temporaryHolidayDto;
    }

    //    RegularHoliday 객체를 DTO로 변환하는 메서드
    public static RegularHolidayDto convertToDto(RegularHoliday regularHoliday) {
        RegularHolidayDto regularHolidayDto = new RegularHolidayDto();
        regularHolidayDto.setId(regularHoliday.getId());
        regularHolidayDto.setDayOfTheWeek(regularHoliday.getDayOfTheWeek() != null ? regularHoliday.getDayOfTheWeek().name() : null);
        regularHolidayDto.setRegularClosedDays(regularHoliday.getRegularClosedDays() != null ? regularHoliday.getRegularClosedDays().name() : null);
        return regularHolidayDto;
    }

    // SalesPause 객체를 DTO로 변환하는 메서드
    public static OperatePauseDto convertToDto(OperatePause operatePause) {
        OperatePauseDto operatePauseDto = new OperatePauseDto();
        operatePauseDto.setId(operatePause.getId());
        operatePauseDto.setSalesPauseStartTime(operatePause.getSalesPauseStartTime());
        operatePauseDto.setSalesPauseEndTime(operatePause.getSalesPauseEndTime());

        // durationMinutes를 계산하여 설정 (임시)
        if (operatePause.getSalesPauseStartTime() != null && operatePause.getSalesPauseEndTime() != null) {
            long duration = Duration.between(
                    operatePause.getSalesPauseStartTime().atDate(LocalDate.now()),
                    operatePause.getSalesPauseEndTime().atDate(LocalDate.now())
            ).toMinutes();
            operatePauseDto.setDurationMinutes((int) duration);
        } else {
            operatePauseDto.setDurationMinutes(null);
        }

        return operatePauseDto;
    }

}
