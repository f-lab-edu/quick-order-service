package com.quickorderservice.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Long uid;
    private Long ownerId;
    private String name;
    private String tel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
