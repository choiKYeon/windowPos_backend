package com.example.windowPos.orderManagement.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    //    메뉴 수량
    private Integer count;

    //    메뉴 옵션 리스트
    private List<MenuOptionDto> menuOptionDtoList = new ArrayList<>();

}
