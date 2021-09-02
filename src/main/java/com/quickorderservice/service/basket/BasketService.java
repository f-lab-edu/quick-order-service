package com.quickorderservice.service.basket;

import com.quickorderservice.dto.basket.BasketDTO;
import com.quickorderservice.dto.basket.BasketMenu;
import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.exception.BasketException;
import com.quickorderservice.service.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class BasketService {

    private final RedisTemplate redisTemplate;
    private final MenuService menuService;
    private final String BASKET_ID = "BASKET";
    private final String BASKET_MENU_ID = "BASKET_MENU";

    public void putMenuInBasket(long memberUid, BasketMenu basketMenu) {
        ListOperations<String, BasketMenu> listOperations = redisTemplate.opsForList();
        String basketMenuKey = getBasketMenuKey(memberUid);
        BasketDTO basketDTO = getBasketByMemberUid(memberUid, basketMenu.getRestaurantUid());
        MenuDTO menu = menuService.getMenuUidAndRestaurantUid(basketMenu.getMenuUid(), basketMenu.getRestaurantUid());

        if (menu == null)
            throw new BasketException("잘못된 식당의 메뉴에 접근하였습니다.");

        if (basketDTO.getRestaurantUid() != basketMenu.getRestaurantUid())
            throw new BasketException("장바구니에 다른 식당의 메뉴를 담을 수 없습니다.");

        listOperations.rightPush(basketMenuKey, basketMenu);
    }

    public List<BasketMenu> getAllBasketMenusByMemberUid(long memberUid) {
        ListOperations<String, BasketMenu> listOperations = redisTemplate.opsForList();
        String key = getBasketMenuKey(memberUid);
        List<BasketMenu> list = listOperations.range(key, 0, listOperations.size(key) - 1);

        if (list == null) {
            list = new LinkedList();
        }

        return list;
    }

    private BasketDTO getBasketByMemberUid(long memberUid, long restaurantUid) {
        ValueOperations<String, BasketDTO> operations = redisTemplate.opsForValue();
        String basketKey = getBasketKey(memberUid);
        BasketDTO basketDTO = operations.get(basketKey);

        if (basketDTO == null) {
            basketDTO = new BasketDTO(memberUid, restaurantUid);
            operations.set(basketKey, basketDTO);
        }

        return basketDTO;
    }

    private String getBasketKey(long memberUid) {
        return BASKET_ID + "_" + memberUid;
    }

    private String getBasketMenuKey(long memberUid) {
        return BASKET_MENU_ID + "_" + memberUid;
    }
}
