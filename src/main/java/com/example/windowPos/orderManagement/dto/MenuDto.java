package com.example.windowPos.orderManagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {

    private Long id;

    //    메뉴 이름
    private String menuName;

    //    메뉴 가격
    private Long price;
}
