package com.example.windowPos.orderManagement.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OrderUpdate extends BaseEntity {

    //    주문 거절 사유
    private String rejectionReason;

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    //    예상 조리시간
    private Integer estimatedCookingTime;

    public void setEstimatedCookingTime(Integer estimatedCookingTime) {
        this.estimatedCookingTime = estimatedCookingTime;
    }

    //    도착 예상 시간
    private Integer estimatedArrivalTime;

    public void setEstimatedArrivalTime(Integer estimatedArrivalTime) {
        this.estimatedArrivalTime = estimatedArrivalTime;
    }

    @OneToOne
    @JoinColumn(name = "order_update_id")
    private OrderManagement orderManagement;

    public void setOrderManagement(OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
    }
}
