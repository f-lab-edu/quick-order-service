package com.quickorderservice.dto.basket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BasketMenu {

    long menuUid;
    long restaurantUid;
    long memberUid;
    int quantity;

}
