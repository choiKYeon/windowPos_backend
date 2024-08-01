package com.example.windowPos.setting.service;

import com.example.windowPos.setting.entity.OperatePause;
import com.example.windowPos.setting.repository.OperatePauseRepository;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OperatePauseService {
    private final OperatePauseRepository operatePauseRepository;

    // 현재 시간이 영업 일시 정지 기간 내에 있는지 확인
    public boolean isSalesPaused() {
        LocalTime now = LocalTime.now();
        OperatePause operatePause = operatePauseRepository.findCurrentSalesPause(now);

        if (operatePause == null || operatePause.getOperateStatus() != OperateStatus.START) {
            return false;
        }
        LocalTime startTime = operatePause.getSalesPauseStartTime();
        LocalTime endTime = operatePause.getSalesPauseEndTime();

        return (startTime != null && now.isAfter(startTime)) &&
                (endTime != null && now.isBefore(endTime));
    }
}
