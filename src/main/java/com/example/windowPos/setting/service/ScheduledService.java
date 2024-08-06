package com.example.windowPos.setting.service;

import com.example.windowPos.setting.entity.*;
import com.example.windowPos.setting.repository.ClosedDaysRepository;
import com.example.windowPos.setting.repository.SettingRepository;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledService {
    private final SettingRepository settingRepository;
    private final ClosedDaysRepository closedDaysRepository;

    //    시간 주기적으로 체크해서, 설정 시간 지난 경우 상태를 업데이트 하는 구문
    @Scheduled(fixedRate = 300000) // 5분마다 체크
    @Transactional
    public void checkUpdateOperateStatus() {
        LocalTime now = LocalTime.now();

        int pageSize = 1000;
        int pageNumber = 0;
//        데이터를 작은 단위로 나눠서 조회하기 위해 페이징 사용, 최대 1000 사이즈
        Page<Setting> settings;
        List<Setting> settingsToUpdate = new ArrayList<>();

        System.out.println("영업 임시중지 시간 상태 확인중입니다.");
//        영업 임시 중지 시간 체크하는 구문
        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            settings = settingRepository.findByOperateStatus(OperateStatus.PAUSE, pageable);

            for (Setting setting : settings) {
                OperatePause operatePause = setting.getOperatePause();
                if (operatePause != null && operatePause.getSalesPauseEndTime() != null) {
                    if (now.isAfter(operatePause.getSalesPauseEndTime())) {
//                        영업 임시 중지 시간 만료되고나면 반복되지 않게 초기화
                        setting.setOperateStatus(OperateStatus.START);
                        operatePause.setSalesPauseStartTime(LocalTime.of(0, 0));
                        operatePause.setSalesPauseEndTime(LocalTime.of(0, 0));
                        settingsToUpdate.add(setting);
                    }
                }
            }
            pageNumber++;
        } while (settings.hasNext());

        if (!settingsToUpdate.isEmpty()) {
            settingRepository.saveAll(settingsToUpdate); // 일괄 업데이트
        }
    }

    //        브레이크 타임 시간 체크하는 구문
    @Scheduled(fixedRate = 180000) // 3분마다 체크
    @Transactional
    public void checkUpdateBreakTime() {
        LocalTime now = LocalTime.now();

        int pageSize = 1000;
        int pageNumber = 0;
        Page<Setting> settings;
        List<Setting> settingList = new ArrayList<>();

        System.out.println("브레이크 타임 상태를 확인하고 있습니다.");

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            settings = settingRepository.findAll(pageable);
            for (Setting setting : settings) {
                BreakTime breakTime = setting.getBreakTime();
                if (breakTime != null) {
                    if (now.isAfter(breakTime.getBreakTimeStart()) && now.isBefore(breakTime.getBreakTimeEnd())) {
//                        브레이크 타임 상태
                        setting.setOperateStatus(OperateStatus.PAUSE);
                    } else {
//                        브레이크 타임이 아닌 상태
                        setting.setOperateStatus(OperateStatus.START);
                    }
                    settingList.add(setting);
                }
            }
            pageNumber++;
        } while (settings.hasNext());

        if (!settingList.isEmpty()) {
            settingRepository.saveAll(settingList);
        }
    }

    //    영업시간 체크하는 구문
    @Scheduled(fixedRate = 180000) // 3분마다 체크
    @Transactional
    public void checkUpdateOperateTime() {
        LocalTime now = LocalTime.now();
//        오늘
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        int pageSize = 1000;
        int pageNumber = 0;
        Page<Setting> settings;
        List<Setting> settingList = new ArrayList<>();

        System.out.println("영업시간 상태를 확인하고 있습니다.");

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            settings = settingRepository.findAll(pageable);
            for (Setting setting : settings) {
                OperateTime operateTime = setting.getOperateTime();
                if (operateTime != null) {
                    boolean open = false;

                    switch (today) {
                        case MONDAY:
                        case TUESDAY:
                        case WEDNESDAY:
                        case THURSDAY:
                        case FRIDAY:
                            if (operateTime.getWeekdayAllDay() != null && operateTime.getWeekdayAllDay()) {
                                open = true;
                            } else if (now.isAfter(operateTime.getWeekdayStartTime()) && now.isBefore(operateTime.getWeekdayEndTime())) {
                                open = true;
                            }
                            break;

                        case SATURDAY:
                            if (operateTime.getWeekdayAllDay() != null && operateTime.getWeekdayAllDay()) {
                                open = true;
                            } else if (now.isAfter(operateTime.getSaturdayStartTime()) &&
                                    now.isBefore(operateTime.getSaturdayEndTime())) {
                                open = true;
                            }
                            break;

                        case SUNDAY:
                            if (operateTime.getWeekdayAllDay() != null && operateTime.getWeekdayAllDay()) {
                                open = true;
                            } else if (now.isAfter(operateTime.getSundayStartTime()) &&
                                    now.isBefore(operateTime.getSundayEndTime())) {
                                open = true;
                            }
                            break;
                    }

                    if (open) {
                        setting.setOperateStatus(OperateStatus.START);
                    } else {
                        setting.setOperateStatus(OperateStatus.END);
                    }
                    settingList.add(setting);
                }
            }
            pageNumber++;
        } while (settings.hasNext());

        if (!settingList.isEmpty()) {
            settingRepository.saveAll(settingList);
        }
    }

    //    휴무일 체크하는 구문
    @Scheduled(fixedRate = 86400000) // 24시간마다 실행
    @Transactional
    public void checkUpdateClosedDays() {
        LocalDate today = LocalDate.now();
        DayOfWeek todayOfWeek = today.getDayOfWeek();
        List<Setting> settingList = settingRepository.findAll();
        boolean close = false;

        System.out.println("휴무일 상태를 확인하고 있습니다.");

        for (Setting setting : settingList) {
            ClosedDays closedDays = setting.getClosedDays();
            if (closedDays != null) {
                close = checkUpdateRegularHolidays(closedDays, todayOfWeek) || checkUpdateTemporaryHolidays(closedDays, today);

                if (close) {
                    setting.setOperateStatus(OperateStatus.END);
                    settingRepository.save(setting);
                }
            }
        }
    }

    //    정기 휴무일
    private boolean checkUpdateRegularHolidays(ClosedDays closedDays, DayOfWeek today) {
        List<RegularHoliday> regularHolidayList = closedDays.getRegularHolidayList();
        if (regularHolidayList != null) {
            for (RegularHoliday regularHoliday : regularHolidayList) {
//                오늘이 정기휴무일이면 영업 중지
                if (regularHoliday.getDayOfTheWeek().equals(today)) {
                    return true;
                }
            }
        }
        return false;
    }

    //    임시 휴무일
    private boolean checkUpdateTemporaryHolidays(ClosedDays closedDays, LocalDate today) {
        List<TemporaryHoliday> temporaryHolidayList = closedDays.getTemporaryHolidayList();
        if (temporaryHolidayList != null) {

//            임시 휴무일 리스트를 순회 조회하기 위한 Iterator 배열
            Iterator<TemporaryHoliday> iterator = temporaryHolidayList.iterator();

//            리스트가 없을 때 까지 반복
            while (iterator.hasNext()) {
                TemporaryHoliday temporaryHoliday = iterator.next();
                LocalDate startDate = temporaryHoliday.getTemporaryHolidayStartDate();
                LocalDate endDate = temporaryHoliday.getTemporaryHolidayEndDate();

//                휴무일이면 영업 종료 , 오늘이 startDate 이전이고, endDate 이후인가
                if (startDate != null && endDate != null && !today.isBefore(startDate) && !today.isAfter(endDate)) {
                    return true;
                }

//                휴무일이 종료되었으면 리스트에서 제거하셈
                if (endDate != null && today.isAfter(endDate)) {
                    iterator.remove();
                }
            }
            closedDaysRepository.save(closedDays);
        }
//        휴무일이 아직 안왔거나 휴무일이 없으면 false 반환임
        return false;
    }
}
