package com.quickorderservice.service.menu;

import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.exception.RegisterException;
import com.quickorderservice.mapper.MenuMapper;
import com.quickorderservice.service.restaurant.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService implements IMenuService {

    private final MenuMapper menuMapper;
    private final RestaurantService restaurantService;

    @Override
    public List<MenuDTO> getAllMenusByRestaurant(long restaurantUid) {
        return menuMapper.selectAllMenuByRestaurantId(restaurantUid);
    }

    @Override
    public MenuDTO getMenuByUid(long restaurantUid, long menuUid) {
        MenuDTO menu = menuMapper.selectMenuByUid(menuUid);

        if (menu == null || menu.getRestaurantId() != restaurantUid)
            throw new NotFoundIdException("존재하지 않는 메뉴 입니다.");

        return menuMapper.selectMenuByUid(menuUid);
    }

    @Override
    public void registerMenu(long ownerUid, long restaurantUid, MenuDTO menu) {
        if (!isMatchedOwnerAndRestaurant(ownerUid, restaurantUid))
            throw new IllegalArgumentException("잘못된 식당에 접근하였습니다.");

        int result = menuMapper.insertMenu(menu, restaurantUid);

        if (result != 1)
            throw new RegisterException("정상적으로 메뉴가 등록되지 않았습니다.");
    }

    private boolean isMatchedOwnerAndRestaurant(long ownerUid, Long restaurantUid) {
        RestaurantDTO restaurant = restaurantService.getRestaurantsByUid(restaurantUid);

        if (restaurant == null)
            throw new NotFoundIdException("식당이 존재하지 않습니다.");

        return restaurant.getOwnerId() == ownerUid;
    }

}
