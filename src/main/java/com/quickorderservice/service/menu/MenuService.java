package com.quickorderservice.service.menu;

import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.mapper.MenuMapper;
import com.quickorderservice.service.restaurant.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;
    private final RestaurantService restaurantService;

    public List<MenuDTO> getAllMenusByRestaurant(Long restaurantUid) {
        return menuMapper.selectAllMenuByRestaurantId(restaurantUid);
    }

    public MenuDTO getMenuByUid(Long menuUid) {
        MenuDTO menu = menuMapper.selectMenuByUid(menuUid);

        if(menu == null)
            throw new NotFoundIdException("존재하지 않는 메뉴 입니다.");

        return menuMapper.selectMenuByUid(menuUid);
    }

    public void registerMenu(long ownerUid, Long restaurantUid, MenuDTO menu) {
        if (!isMatchedOwnerAndRestaurant(ownerUid, restaurantUid))
            throw new IllegalArgumentException("잘못된 식당에 접근하였습니다.");

        menuMapper.insertMenu(menu, restaurantUid);
    }

    private boolean isMatchedOwnerAndRestaurant(long ownerUid, Long restaurantUid) {
        RestaurantDTO restaurant = restaurantService.getRestaurantsByUid(restaurantUid);

        if (restaurant == null)
            throw new NotFoundIdException("식당이 존재하지 않습니다.");

        return restaurant.getOwnerId() == ownerUid;
    }
}
