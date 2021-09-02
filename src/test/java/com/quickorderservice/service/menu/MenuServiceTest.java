package com.quickorderservice.service.menu;

import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.exception.RegisterException;
import com.quickorderservice.mapper.MenuMapper;
import com.quickorderservice.service.restaurant.RestaurantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    MenuMapper menuMapper;
    @Mock
    RestaurantService restaurantService;
    @InjectMocks
    CacheMenuService menuService;

    @Test
    @DisplayName("정상적으로 메뉴를 등록한다.")
    public void registerMenu() {
        MenuDTO menu = new MenuDTO(1L, 1L, "menu", 111, 123, null, null);
        OwnerDTO owner = new OwnerDTO(1L, "owner", "1234", "test", "-", "-", null, null);

        RestaurantDTO restaurant = new RestaurantDTO(1L, 1L, "res", "-", null, 0, 0, null, null, null);

        when(restaurantService.getRestaurantsByUid(1L)).thenReturn(restaurant);
        when(menuMapper.insertMenu(menu, restaurant.getUid())).thenReturn(1);
        menuService.registerMenu(owner.getUid(), restaurant.getUid(), menu);
    }

    @Test
    @DisplayName("존재하지 않는 Restaurant에 메뉴 등록 시 예외가 발생한다..")
    public void registerMenuWithNotExistRestaurant() {
        MenuDTO menu = new MenuDTO(1L, 1L, "menu", 111, 123, null, null);
        OwnerDTO owner = new OwnerDTO(1L, "owner", "1234", "test", "-", "-", null, null);

        RestaurantDTO restaurant = new RestaurantDTO(1L, 1L, "res", "-", null, 0, 0, null, null, null);

        when(restaurantService.getRestaurantsByUid(restaurant.getUid())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> menuService.registerMenu(owner.getUid(), restaurant.getUid(), menu));
    }

    @Test
    @DisplayName("Owner가 소유하지 않은 Restaurant에 메뉴 등록 시 예외가 발생한다..")
    public void registerMenuWithWrongRestaurant() {
        MenuDTO menu = new MenuDTO(1L, 1L, "menu", 111, 123, null, null);
        OwnerDTO owner = new OwnerDTO(1L, "owner", "1234", "test", "-", "-", null, null);

        RestaurantDTO wrongRestaurant = new RestaurantDTO(1L, 1L, "res", "-", null, 0, 0, null, null, null);

        when(restaurantService.getRestaurantsByUid(1L)).thenReturn(wrongRestaurant);

        assertThrows(RegisterException.class, () -> {
            menuService.registerMenu(owner.getUid(), wrongRestaurant.getUid(), menu);
        });
    }

}
