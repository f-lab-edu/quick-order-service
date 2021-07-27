package com.quickorderservice.controller;

import com.quickorderservice.annotation.MemberId;
import com.quickorderservice.dto.basket.BasketMenu;
import com.quickorderservice.service.basket.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping("/baskets")
    public void putMenuInBasket(@MemberId long memberUid, @RequestBody BasketMenu basketMenu) {
        basketService.putMenuInBasket(memberUid, basketMenu);
    }

    @GetMapping("/baskets")
    public Map<Long, BasketMenu> getAllMenus(@MemberId long memberUid) {
        return basketService.getAllBasketMenusByMemberUid(memberUid);
    }
}
