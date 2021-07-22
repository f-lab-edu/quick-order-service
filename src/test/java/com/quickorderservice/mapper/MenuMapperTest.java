package com.quickorderservice.mapper;


import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.service.owner.OwnerService;
import com.quickorderservice.service.restaurant.RestaurantService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class MenuMapperTest {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    OwnerService ownerService;
    OwnerDTO owner;
    RestaurantDTO restaurant;

    @BeforeEach
    void before() {
        owner = new OwnerDTO(null, "owner", "1234", "test", "-", "-", null, null);
        ownerService.joinOwner(owner);
        owner = ownerService.findOwnerById("owner");

        restaurant = new RestaurantDTO(null, null, "res", "-", null, null);
        restaurantService.registerRestaurant(owner.getUid(), restaurant);
        restaurant = restaurantService.getRestaurantsByOwnerId(owner.getUid()).get(0);
    }

    @Test
    @DisplayName("정상적으로 메뉴 등록시 1을 반환한다.")
    void insertMenu() {
        MenuDTO menu = new MenuDTO(0, 0, "menu", 100, 100, null, null);
        int result = menuMapper.insertMenu(menu, restaurant.getUid());

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("정상적으로 restaurant의 UID로 메뉴 조회시 메뉴 리스트를 반환한다.")
    void selectAllMenuByRestaurantId() {
        MenuDTO menu = new MenuDTO(0,0, "menu", 100, 100, null, null);
        menuMapper.insertMenu(menu, restaurant.getUid());
        menuMapper.insertMenu(menu, restaurant.getUid());

        List<MenuDTO> menus = menuMapper.selectAllMenuByRestaurantId(restaurant.getUid());

        Assertions.assertThat(menus.size()).isEqualTo(2);
    }

}