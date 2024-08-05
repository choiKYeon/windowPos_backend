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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperatePauseService {
    private final SettingRepository settingRepository;
    private final MemberService memberService;
    private final OperatePauseRepository operatePauseRepository;

    //    현재 영업의 상태를 확인하는 구문
    public OperateStatus operateStatus() {
        Member currentMember = memberService.getCurrentMember();
        Setting setting = settingRepository.findByMember(currentMember)
                .orElseThrow(() -> new EntityNotFoundException("로그인 계정에서 세팅정보 못찾는당"));

        return setting.getOperateStatus();
    }

    //    시간 주기적으로 체크해서, 설정 시간 지난 경우 상태를 업데이트 하는 구문
    @Scheduled(fixedRate = 300000) // 5분마다 실행
    @Transactional
    public void checkUpdateOperateStatus() {
        LocalTime now = LocalTime.now();

        int pageSize = 1000;
        int pageNumber = 0;
//        데이터를 작은 단위로 나눠서 조회하기 위해 페이징 사용, 최대 1000 사이즈
        Page<Setting> settings;
        List<Setting> settingsToUpdate = new ArrayList<>();

        System.out.println("잘 확인중입니다.");

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            settings = settingRepository.findByOperateStatus(OperateStatus.PAUSE, pageable);

            for (Setting setting : settings) {
                OperatePause operatePause = setting.getOperatePause();
                if (operatePause != null && operatePause.getSalesPauseEndTime() != null) {
                    if (now.isAfter(operatePause.getSalesPauseEndTime())) {
                        setting.setOperateStatus(OperateStatus.START);
                        settingsToUpdate.add(setting);
                    }
                }
            }
            pageNumber++;
        }  while (settings.hasNext());

        if (!settingsToUpdate.isEmpty()) {
            settingRepository.saveAll(settingsToUpdate); // 일괄 업데이트
        }
    }

    // 현재 시간이 영업 일시 정지 기간 내에 있는지 확인
    public boolean isSalesPaused() {
        Member currentMember = memberService.getCurrentMember();
        Setting setting = settingRepository.findByMember(currentMember)
                .orElseThrow(() -> new EntityNotFoundException("로그인 계정에서 세팅정보 못찾는당"));

        OperatePause operatePause = setting.getOperatePause();
        if (operatePause == null) {
            return false;
        }

        LocalTime now = LocalTime.now();
        LocalTime startTime = operatePause.getSalesPauseStartTime();
        LocalTime endTime = operatePause.getSalesPauseEndTime();

        if (startTime != null && endTime != null && now.isAfter(startTime) && now.isBefore(endTime)) {
            return setting.getOperateStatus() == OperateStatus.PAUSE;
        }

        return false;
    }

    //    영업 임시 중지 구문
    public void changeOperateStatus(Long id, OperatePauseDto operatePauseDto) {
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
                .salesPauseStartTime(LocalTime.now())
                .salesPauseEndTime(endTime)
                .build();

        operatePause.setSetting(setting);

        operatePauseRepository.save(operatePause);
        settingRepository.save(setting);

    }
}
