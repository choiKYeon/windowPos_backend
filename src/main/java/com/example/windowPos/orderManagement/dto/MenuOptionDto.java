package com.example.windowPos.orderManagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOptionDto {

    private Long id;

    //    옵션 이름
    private String optionName;

    //    옵션 가격
    private Long optionPrice;
}
