package com.example.windowPos.setting.repository;

import com.example.windowPos.member.entity.Member;
import com.example.windowPos.setting.entity.Setting;
import com.example.windowPos.setting.settingEnum.OperateStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByMember(Member member);

    List<Setting> findByOperateStatus(OperateStatus operateStatus);

    Page<Setting> findByOperateStatus(OperateStatus operateStatus, Pageable pageable);
}
