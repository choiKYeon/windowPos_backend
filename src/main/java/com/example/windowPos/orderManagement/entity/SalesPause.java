package com.example.windowPos.orderManagement.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class SalesPause extends BaseEntity {

    //    영업 상태
    private String salesStatus;

    // 영업 일시 정지 시작 시간
    private LocalDateTime salesPauseStartTime;

    // 영업 일시 정지 종료 시간
    private LocalDateTime salesPauseEndTime;

    @OneToOne
    @JoinColumn(name = "order_management_id")
    private OrderManagement orderManagement;
}
