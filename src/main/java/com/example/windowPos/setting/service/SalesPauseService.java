package com.example.windowPos.setting.service;

import com.example.windowPos.setting.entity.OperatePause;
import com.example.windowPos.setting.repository.SalesPauseRepository;
import com.example.windowPos.setting.settingEnum.SalesStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SalesPauseService {
    private final SalesPauseRepository salesPauseRepository;

    // 현재 시간이 영업 일시 정지 기간 내에 있는지 확인
    public boolean isSalesPaused() {
        OperatePause operatePause = salesPauseRepository.findCurrentSalesPause();

        if (operatePause == null || operatePause.getSalesStatus() != SalesStatus.START) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return (operatePause.getSalesPauseStartTime() != null && now.isAfter(operatePause.getSalesPauseStartTime())) &&
                (operatePause.getSalesPauseEndTime() != null && now.isBefore(operatePause.getSalesPauseEndTime()));
    }
}
