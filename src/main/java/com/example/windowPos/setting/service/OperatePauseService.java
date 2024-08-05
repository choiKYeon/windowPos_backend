package com.example.windowPos.setting.service;

import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.service.MemberService;
import com.example.windowPos.setting.dto.OperatePauseDto;
import com.example.windowPos.setting.entity.OperatePause;
import com.example.windowPos.setting.entity.Setting;
import com.example.windowPos.setting.repository.OperatePauseRepository;
import com.example.windowPos.setting.repository.SettingRepository;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OperatePauseService {
    private final SettingRepository settingRepository;
    private final MemberService memberService;
    private final OperatePauseRepository operatePauseRepository;

    // 현재 시간이 영업 일시 정지 기간 내에 있는지 확인
    public boolean isSalesPaused() {
        Member currentMember = memberService.getCurrentMember();
        Setting setting = settingRepository.findByMember(currentMember)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found for current member"));

        OperatePause operatePause = setting.getOperatePause();
        if (operatePause == null || operatePause.getOperateStatus() != OperateStatus.START) {
            return false;
        }

        LocalTime now = LocalTime.now();
        LocalTime startTime = operatePause.getSalesPauseStartTime();
        LocalTime endTime = operatePause.getSalesPauseEndTime();

        return (startTime != null && now.isAfter(startTime)) && (endTime != null && now.isBefore(endTime));
    }

//    영업 임시 중지 구문
    public void changeOperateStatus(Long id ,OperatePauseDto operatePauseDto) {
        Setting setting = settingRepository.findById(id).orElse(null);

        LocalTime now = LocalTime.now();
        LocalTime endTime;
        
        if (operatePauseDto.getSalesPauseEndTime() != null) {
            endTime = operatePauseDto.getSalesPauseEndTime();
        } else if (operatePauseDto.getDurationMinutes() != null) {
            endTime = now.plus(Duration.ofMinutes(operatePauseDto.getDurationMinutes()));
        } else {
            throw new IllegalArgumentException("아오 또 오류터졌네 짜증나게, 한번에 되는일이없네 ㅠㅠ");
        }

        OperatePause operatePause = OperatePause.builder()
                .operateStatus(OperateStatus.PAUSE)
                .salesPauseEndTime(endTime)
                .build();

        operatePause.setSetting(setting);

        operatePauseRepository.save(operatePause);
        settingRepository.save(setting);
    }
}
