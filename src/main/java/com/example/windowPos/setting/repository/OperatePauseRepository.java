package com.example.windowPos.setting.repository;

import com.example.windowPos.setting.entity.OperatePause;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatePauseRepository extends JpaRepository<OperatePause, Long> {

}
