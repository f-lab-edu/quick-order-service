package com.quickorderservice.service.basket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickorderservice.dto.basket.BasketDTO;
import com.quickorderservice.dto.basket.BasketMenu;
import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.exception.BasketException;
import com.quickorderservice.service.menu.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BasketService {

    private final RedisTemplate redisTemplate;
    private final MenuService menuService;
    private final String BASKET_ID = "BASKET";
    private final String BASKET_MENU_ID = "BASKET_MENU";

    public void putMenuInBasket(long memberUid, BasketMenu basketMenu) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String basketMenuKey = getBasketMenuKey(memberUid);
        BasketDTO basketDTO = getBasketByMemberUid(memberUid, basketMenu.getRestaurantUid());
        MenuDTO menu = menuService.getMenuUidAndRestaurantUid(basketMenu.getMenuUid(), basketMenu.getRestaurantUid());

        if (menu == null)
            throw new BasketException("잘못된 식당의 메뉴에 접근하였습니다.");

        if (basketDTO.getRestaurantUid() != basketMenu.getRestaurantUid())
            throw new BasketException("장바구니에 다른 식당의 메뉴를 담을 수 없습니다.");

        hashOperations.put(basketMenuKey, basketMenu.getMenuUid(), basketMenu);
    }

    public Map<Long, BasketMenu> getAllBasketMenusByMemberUid(long memberUid) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = getBasketMenuKey(memberUid);
        return hashOperations.entries(key);
    }

    private BasketDTO getBasketByMemberUid(long memberUid, long restaurantUid) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        ObjectMapper objectMapper = new ObjectMapper();
        String basketKey = getBasketKey(memberUid);
        String jsonBasketMenu = operations.get(basketKey);
        BasketDTO basketDTO;

        if (jsonBasketMenu != null) {
            try {
                basketDTO = objectMapper.readValue(jsonBasketMenu, BasketDTO.class);
            } catch (JsonProcessingException e) {
                throw new BasketException("장바구니를 불러오는데 오류가 발생하였습니다.");
            }
        } else {
            basketDTO = new BasketDTO(memberUid, restaurantUid);
            try {
                jsonBasketMenu = objectMapper.writeValueAsString(basketDTO);
            } catch (JsonProcessingException e) {
                throw new BasketException("장바구니를 불러오는데 오류가 발생하였습니다.");
            }

            operations.set(basketKey, jsonBasketMenu);
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
