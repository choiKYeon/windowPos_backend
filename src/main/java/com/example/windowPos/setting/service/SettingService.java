package com.example.windowPos.setting.service;

import com.example.windowPos.member.entity.Member;
import com.example.windowPos.member.repository.MemberRepository;
import com.example.windowPos.setting.dto.SettingDto;
import com.example.windowPos.setting.entity.Setting;
import com.example.windowPos.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.windowPos.dtoConverter.DtoConverter.convertToDto;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;
    private final MemberRepository memberRepository;

//    유저의 세팅환경 갖고오쇼
    public SettingDto getSettingByMember (Member member) {
        Setting setting = settingRepository.findByMember(member).orElse(null);

        return convertToDto(setting);
    }

//    유저 세팅환경 업데이트유 (수정중)
    @Transactional
    public void updateSetting(Long id, SettingDto settingDto) {
        Member member = memberRepository.findById(id).orElse(null);
        Setting setting = settingRepository.findByMember(member).orElse(null);

        if (settingDto.getOperatePauseDto() != null) {

        }
    }
}
