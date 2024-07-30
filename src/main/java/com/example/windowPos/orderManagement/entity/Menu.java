package com.example.windowPos.orderManagement.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Menu extends BaseEntity {

//    메뉴 이름
    private String menuName;

//    메뉴 가격
    private Long price;

    //    @JsonBackReference는 순환참조 에러를 해결하기 위한 방법
    @ManyToOne
    @JoinColumn(name = "order_management_id")
    @JsonBackReference
    private OrderManagement orderManagement;
}
