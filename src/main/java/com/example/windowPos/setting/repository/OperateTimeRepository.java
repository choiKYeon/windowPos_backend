package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.OperateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperateTimeRepository extends JpaRepository<OperateTime, Long> {
}
