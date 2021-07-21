package com.quickorderservice.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuDTO {

    private long uid;
    private long restaurantId;
    private String name;
    private int price;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}