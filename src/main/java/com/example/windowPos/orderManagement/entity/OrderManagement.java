package com.example.windowPos.orderManagement.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.example.windowPos.orderManagement.orderEnum.OrderStatus;
import com.example.windowPos.orderManagement.orderEnum.OrderType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class OrderManagement extends BaseEntity {

    //    주문 시간
    private LocalDateTime orderTime;

    //    요청사항
    private String request;

    //    고객 주소
    private String address;

    //    총 금액
    private Long totalPrice;

    //    주문 번호
    @Column(unique = true)
    private Long orderNumber;

    //    주문 상태관리
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    //    포장인지 배달인지 주문 타입
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    //    주문 거절 사유
    private String rejectionReason;

    //    예상 조리시간
    private Integer estimatedCookingTime;

    //    도착 예정 시간
    private LocalDateTime estimatedArrivalTime;

    //    영업 중단
    @OneToOne(mappedBy = "orderManagement", cascade = CascadeType.ALL)
    private SalesPause salesPause;

    //    메뉴 리스트
    //    @JsonManagedReference는 순환참조 에러를 해결하기 위한 방법
    @OneToMany(mappedBy = "orderManagement", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Menu> menuList;

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public void acceptOrder() {
        if (this.orderStatus == OrderStatus.WAITING) {
            this.orderStatus = OrderStatus.IN_PROGRESS;
        } else {
            throw new IllegalStateException("주문을 수락할 수 없는 상태입니다.");
        }
    }

    public void rejectOrder() {
        if (this.orderStatus == OrderStatus.WAITING) {
            this.orderStatus = OrderStatus.REJECTED;
        } else {
            throw new IllegalStateException("주문을 거절할 수 없는 상태입니다.");
        }
    }

    public void cancelOrder() {
        if (this.orderStatus == OrderStatus.IN_PROGRESS) {
            this.orderStatus = OrderStatus.REJECTED;
        } else {
            throw new IllegalStateException("주문을 취소할 수 없는 상태입니다.");
        }
    }

    public void completeOrder() {
        if (this.orderStatus == OrderStatus.IN_PROGRESS) {
            this.orderStatus = OrderStatus.COMPLETED;
        } else {
            throw new IllegalStateException("주문을 완료할 수 없는 상태입니다.");
        }
    }

//    주문표 인쇄도 만들어야함
//    도착 남은 시간 업데이트 로직 짜야함

}
