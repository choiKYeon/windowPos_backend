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
public class MenuOption extends BaseEntity {

    //    옵션 이름
    private String optionName;

    //    옵션 가격
    private Long optionPrice;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
