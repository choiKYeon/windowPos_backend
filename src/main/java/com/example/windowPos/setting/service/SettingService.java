package com.example.windowPos.setting.service;

import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.repository.MemberRepository;
import com.example.windowPos.setting.dto.*;
import com.example.windowPos.setting.entity.*;
import com.example.windowPos.setting.repository.ClosedDaysRepository;
import com.example.windowPos.setting.repository.SettingRepository;
import com.example.windowPos.setting.settingEnum.DayOfTheWeek;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import com.example.windowPos.setting.settingEnum.RegularClosedDays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.windowPos.dtoConverter.DtoConverter.convertToDto;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;
    private final MemberRepository memberRepository;
    private final ClosedDaysRepository closedDaysRepository;


//    세팅 세부 클래스 기본 설정
    @Transactional
    public void newSettingLogin(Setting setting) {
            setting.getOperatePause().setSetting(setting);
            setting.getOperateTime().setSetting(setting);
            setting.getEstimatedCookingTime().setSetting(setting);
            setting.getEstimatedArrivalTime().setSetting(setting);
            setting.getClosedDays().setSetting(setting);
            setting.getBreakTime().setSetting(setting);
    }

    //    유저의 세팅환경 갖고오쇼
    public SettingDto getSettingByMember(Member member) {
        Setting setting = settingRepository.findByMember(member).orElse(null);

        return convertToDto(setting);
    }

    //    유저 세팅환경 업데이트 입늬다~
    @Transactional
    public void updateSetting(Long id, SettingDto settingDto) {
        Member member = memberRepository.findById(id).orElse(null);
        Setting setting = settingRepository.findByMember(member).orElse(null);

        updateBreakTime(setting, settingDto.getBreakTimeDto());
        updateClosedDays(setting, settingDto.getClosedDaysDto());
        updateEstimatedArrivalTime(setting, settingDto.getEstimatedArrivalTimeDto());
        updateEstimatedCookingTime(setting, settingDto.getEstimatedCookingTimeDto());
        updateOperateTime(setting, settingDto.getOperateTimeDto());
        updateOperatePause(setting, settingDto.getOperatePauseDto());

        settingRepository.save(setting);
    }

    //    브레이크 타임 업데이트 구문
    private void updateBreakTime(Setting setting, BreakTimeDto breakTimeDto) {
        if (breakTimeDto != null) {
            BreakTime breakTime = setting.getBreakTime();
            if (breakTimeDto.getBreakTimeStart() != null) {
                breakTime.setBreakTimeStart(breakTimeDto.getBreakTimeStart());
            }
            if (breakTimeDto.getBreakTimeEnd() != null) {
                breakTime.setBreakTimeEnd(breakTimeDto.getBreakTimeEnd());
            }
            breakTime.setSetting(setting);
        }
    }

    //    휴무일 업데이트 구문
    private void updateClosedDays(Setting setting, ClosedDaysDto closedDaysDto) {
        if (closedDaysDto != null) {
            ClosedDays closedDays = setting.getClosedDays();
            if (closedDaysDto.getTemporaryHolidayDates() != null) {
                List<TemporaryHoliday> updatedHolidays = updateTemporaryHolidays(closedDays, closedDaysDto.getTemporaryHolidayDates());
                closedDays.setTemporaryHolidayList(updatedHolidays);
            }
            if (closedDaysDto.getRegularHolidayList() != null) {
                List<RegularHoliday> updatedHolidays = updateRegularHolidays(closedDays, closedDaysDto.getRegularHolidayList());
                closedDays.setRegularHolidayList(updatedHolidays);
            }
            closedDays.setSetting(setting);
            closedDaysRepository.save(closedDays);
        }
    }

    //    예상 도착 시간 업데이트 구문
    private void updateEstimatedArrivalTime(Setting setting, EstimatedArrivalTimeDto estimatedArrivalTimeDto) {
        if (estimatedArrivalTimeDto != null) {
            EstimatedArrivalTime estimatedArrivalTime = setting.getEstimatedArrivalTime();
            if (estimatedArrivalTimeDto.getEstimatedArrivalTime() != null) {
                estimatedArrivalTime.setEstimatedArrivalTime(estimatedArrivalTimeDto.getEstimatedArrivalTime());
            }
            if (estimatedArrivalTimeDto.getEstimatedArrivalTimeControl() != null) {
                estimatedArrivalTime.setEstimatedArrivalTimeControl(estimatedArrivalTimeDto.getEstimatedArrivalTimeControl());
            }
            estimatedArrivalTime.setSetting(setting);
        }
    }

    //    예상 조리 시간 업데이트 구문
    private void updateEstimatedCookingTime(Setting setting, EstimatedCookingTimeDto estimatedCookingTimeDto) {
        if (estimatedCookingTimeDto != null) {
            EstimatedCookingTime estimatedCookingTime = setting.getEstimatedCookingTime();
            if (estimatedCookingTimeDto.getEstimatedCookingTime() != null) {
                estimatedCookingTime.setEstimatedCookingTime(estimatedCookingTimeDto.getEstimatedCookingTime());
            }
            if (estimatedCookingTimeDto.getEstimatedCookingTimeControl() != null) {
                estimatedCookingTime.setEstimatedCookingTimeControl(estimatedCookingTimeDto.getEstimatedCookingTimeControl());
            }
            estimatedCookingTime.setSetting(setting);
        }
    }

    //    영업 시간 업데이트 구문
    private void updateOperateTime(Setting setting, OperateTimeDto operateTimeDto) {
        if (operateTimeDto != null) {
            OperateTime operateTime = setting.getOperateTime();
//            평일 시간 업데이트
            if (operateTimeDto.getWeekdayAllDay() != null) {
                operateTime.setWeekdayAllDay(operateTimeDto.getWeekdayAllDay());
            }
            if (operateTimeDto.getWeekdayStartTime() != null) {
                operateTime.setWeekdayStartTime(operateTimeDto.getWeekdayStartTime());
            }
            if (operateTimeDto.getWeekdayEndTime() != null) {
                operateTime.setWeekdayEndTime(operateTimeDto.getWeekdayEndTime());
            }
//            토요일 시간 업데이트
            if (operateTimeDto.getSaturdayAllDay() != null) {
                operateTime.setSaturdayAllDay(operateTimeDto.getSaturdayAllDay());
            }
            if (operateTimeDto.getSaturdayStartTime() != null) {
                operateTime.setSaturdayStartTime(operateTimeDto.getSaturdayStartTime());
            }
            if (operateTimeDto.getSaturdayEndTime() != null) {
                operateTime.setSaturdayEndTime(operateTimeDto.getSaturdayEndTime());
            }
//            일요일 시간 업데이트
            if (operateTimeDto.getSundayAllDay() != null) {
                operateTime.setSundayAllDay(operateTimeDto.getSundayAllDay());
            }
            if (operateTimeDto.getSundayStartTime() != null) {
                operateTime.setSundayStartTime(operateTimeDto.getSundayStartTime());
            }
            if (operateTimeDto.getSundayEndTime() != null) {
                operateTime.setSundayEndTime(operateTimeDto.getSundayEndTime());
            }
            operateTime.setSetting(setting);
        }
    }

    //    영업 임시 중지 업데이트 구문
    private void updateOperatePause(Setting setting, OperatePauseDto operatePauseDto) {
        if (operatePauseDto != null) {
            OperatePause operatePause = setting.getOperatePause();
            if (operatePause.getOperateStatus() != OperateStatus.START) {
                if (operatePauseDto.getSalesPauseStartTime() != null) {
                    operatePause.setSalesPauseStartTime(operatePauseDto.getSalesPauseStartTime());
                }
                if (operatePauseDto.getSalesPauseEndTime() != null) {
                    operatePause.setSalesPauseEndTime(operatePauseDto.getSalesPauseEndTime());
                } else if (operatePauseDto.getDurationMinutes() != null) {
                    LocalTime now = LocalTime.now();
                    LocalTime endTime = now.plus(Duration.ofMinutes(operatePauseDto.getDurationMinutes()));
                    operatePause.setSalesPauseEndTime(endTime);
                }
            }
            if (operatePauseDto.getOperateStatus() != null) {
                operatePause.setOperateStatus(OperateStatus.valueOf(operatePauseDto.getOperateStatus()));
            }
            operatePause.setSetting(setting);
        }
    }

    //    정기 휴무 업데이트 구문
    private List<RegularHoliday> updateRegularHolidays(ClosedDays closedDays, List<RegularHolidayDto> regularHolidayDtoList) {
        List<RegularHoliday> regularHolidays = new ArrayList<>();

//        기존 정기휴무 정보 싹 밀어버림~
        closedDays.getRegularHolidayList().clear();

//        새로운 정기 휴무일 데이터 넣어~
        if (regularHolidayDtoList != null) {
            for (RegularHolidayDto regularHolidayDto : regularHolidayDtoList) {
                RegularHoliday regularHoliday = new RegularHoliday();
                regularHoliday.setRegularClosedDays(RegularClosedDays.valueOf(regularHolidayDto.getRegularClosedDays()));
                regularHoliday.setDayOfTheWeek(DayOfTheWeek.valueOf(regularHolidayDto.getDayOfTheWeek()));
                regularHoliday.setClosedDays(closedDays);
                regularHolidays.add(regularHoliday);
            }
        }

        return regularHolidays;
    }

    //    임시 휴무 업데이트 구문
    private List<TemporaryHoliday> updateTemporaryHolidays(ClosedDays closedDays, List<TemporaryHolidayDto> temporaryHolidayDtoList) {
        List<TemporaryHoliday> updatedHolidays = new ArrayList<>();

//        기존 임시휴무 정보 싹 밀어버림 (시원~)
        closedDays.getTemporaryHolidayList().clear();

//        새로운 임시 휴무일 데이터 집어넣기이~
        if (temporaryHolidayDtoList != null) {
            for (TemporaryHolidayDto temporaryHolidayDto : temporaryHolidayDtoList) {
                TemporaryHoliday updatedHoliday = new TemporaryHoliday();
                updatedHoliday.setTemporaryHolidayStartDate(temporaryHolidayDto.getTemporaryHolidayStartDate());
                updatedHoliday.setTemporaryHolidayEndDate(temporaryHolidayDto.getTemporaryHolidayEndDate());
                updatedHoliday.setClosedDays(closedDays);
                updatedHolidays.add(updatedHoliday);
            }
        }

        return updatedHolidays;
    }
}
