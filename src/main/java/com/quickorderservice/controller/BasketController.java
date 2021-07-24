package com.quickorderservice.controller;

import com.quickorderservice.annotation.MemberId;
import com.quickorderservice.dto.basket.BasketDTO;
import com.quickorderservice.dto.basket.BasketMenuDTO;
import com.quickorderservice.service.basket.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/baskets")
    public BasketDTO getMenuInBasket(@MemberId long memberUid) {
        return basketService.getBasket(memberUid);
    }

    @PostMapping("/baskets/menus")
    public void addMenuInBasket(@MemberId long memberUid, @RequestBody BasketMenuDTO menu) {
        basketService.addMenuInBasket(memberUid, menu);
    }

    @GetMapping("/baskets/menus")
    public List<BasketMenuDTO> getAllMenuInBasket(@MemberId long memberUid) {
        return basketService.getAllMenuInBasket(memberUid);
    }

    @PatchMapping("/baskets/menus")
    public void changeMenuQuantityInBasket(@MemberId long memberUid, long menuUid, int quantity) {
        basketService.changeQuantity(memberUid, menuUid, quantity);
    }

    @DeleteMapping("/baskets/menus")
    public void deleteMenuInBasket(@MemberId long memberUid, Long menuUid) {
        basketService.deleteMenuInBasket(memberUid, menuUid);
    }

    @DeleteMapping("/baskets")
    public void deleteBasket(@MemberId long memberUid) {
        basketService.deleteBasket(memberUid);
    }
}
