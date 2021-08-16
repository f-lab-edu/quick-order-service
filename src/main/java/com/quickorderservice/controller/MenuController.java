package com.quickorderservice.controller;

import com.quickorderservice.annotation.OwnerId;
import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.service.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurants")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{restaurantUid}/menus")
    public void registerMenus(@OwnerId long ownerUid, @PathVariable Long restaurantUid, @RequestBody MenuDTO menu) {
        menuService.registerMenu(ownerUid, restaurantUid, menu);
    }

    @GetMapping("/{restaurantUid}/menus")
    public List<MenuDTO> getAllMenusByRestaurant(@PathVariable Long restaurantUid) {
        return menuService.getAllMenusByRestaurant(restaurantUid);
    }

    @GetMapping("/{restaurantUid}/menus/{menuUid}")
    public MenuDTO getMenuByUid(@PathVariable Long restaurantUid, @PathVariable Long menuUid) {
        return menuService.getMenuByUid(restaurantUid, menuUid);
    }
}
