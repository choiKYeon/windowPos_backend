package com.example.windowPos.orderManagement.entity;

import com.example.windowPos.global.baseentity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    //    메뉴 수량
    private Integer count;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MenuOption> menuOptions = new ArrayList<>();

//    메뉴 옵션 설정
    public void setMenuOptions(List<MenuOption> menuOptions) {
        this.menuOptions = menuOptions;
        if (menuOptions != null) {
            for (MenuOption option : menuOptions) {
                option.setMenu(this);
            }
        }
    }

//    옵션 추가
    public void addMenuOption(MenuOption option) {
        if (menuOptions == null) {
            menuOptions = new ArrayList<>();
        }
        menuOptions.add(option);
        option.setMenu(this);
    }

    // 수량 증가 메서드
    public void increaseCount(int amount) {
        this.count += amount;
    }

    // 수량 감소 메서드
    public void decreaseCount(int amount) {
        if (this.count < amount) {
            throw new IllegalArgumentException("수량이 부족합니다.");
        }
        this.count -= amount;
    }

    //    @JsonBackReference는 순환참조 에러를 해결하기 위한 방법
    @ManyToOne
    @JoinColumn(name = "order_management_id")
    @JsonBackReference
    private OrderManagement orderManagement;

    public void setOrderManagement(OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
    }
}
