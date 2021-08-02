package com.quickorderservice.dto.restaurant;

import com.quickorderservice.enumdata.RestaurantCategory;
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
    private String address;
    private double latitude;
    private double longitude;
    private RestaurantCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
