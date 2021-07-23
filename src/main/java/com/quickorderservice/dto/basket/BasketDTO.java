package com.quickorderservice.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDTO {

    private long memberUid;
    private long restaurantUid;

}
