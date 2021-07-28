package com.quickorderservice.service.basket;

import com.quickorderservice.dto.basket.BasketMenu;
import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.exception.BasketException;
import com.quickorderservice.service.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BasketService {

    private final RedisTemplate redisTemplate;
    private final MenuService menuService;
    private final String BASKET_ID = "BASKET";

    public void putMenuInBasket(long memberUid, BasketMenu basketMenu) {
        ValueOperations<String, Map<Long, BasketMenu>> valueOperations = redisTemplate.opsForValue();
        String key = getKey(memberUid);

        MenuDTO menu = menuService.getMenuUidAndRestaurantUid(basketMenu.getMenuUid(), basketMenu.getRestaurantUid());
        if (menu == null) {
            throw new BasketException("잘못된 식당의 메뉴에 접근하였습니다.");
        }

        Map<Long, BasketMenu> basketMenuMap = valueOperations.get(key);

        if (basketMenuMap == null) {
            basketMenuMap = new HashMap();
        } else if (basketMenuMap.get(basketMenuMap.keySet().toArray()[0]).getRestaurantUid() != basketMenu.getRestaurantUid()) {
            throw new BasketException("다른 식당의 메뉴는 등록할 수 없습니다.");
        }

        basketMenuMap.put(basketMenu.getMenuUid(), basketMenu);
        valueOperations.set(key, basketMenuMap);
    }

    public Map<Long, BasketMenu> getAllBasketMenusByMemberUid(long memberUid) {
        ValueOperations<String, Map<Long, BasketMenu>> valueOperations = redisTemplate.opsForValue();
        String key = getKey(memberUid);

        return valueOperations.get(key);
    }

    private String getKey(long memberUid) {
        return BASKET_ID + "_" + memberUid;
    }
}
