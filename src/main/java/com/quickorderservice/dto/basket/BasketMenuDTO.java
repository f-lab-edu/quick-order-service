package com.quickorderservice.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasketMenuDTO {

    private long restaurantUid;
    private long menuUid;
    private int quantity;

}
