package com.quickorderservice.service.basket;

import com.quickorderservice.dto.basket.BasketDTO;
import com.quickorderservice.dto.basket.BasketMenuDTO;
import com.quickorderservice.exception.BasketException;
import com.quickorderservice.mapper.BasketMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class BasketService {

    private final BasketMapper basketMapper;

    public void addMenuInBasket(long memberUid, BasketMenuDTO basketMenu) {
        BasketDTO basket = getBasket(memberUid);

        if (basket == null) {
            makeBasket(memberUid, basketMenu.getRestaurantUid());
            basket = getBasket(memberUid);
        }

        if (basket.getRestaurantUid() != basketMenu.getRestaurantUid())
            throw new BasketException("다른 식당의 메뉴는 장바구니에 추가할 수 없습니다.");

        BasketMenuDTO menuInBasket = getMenuInBasket(memberUid, basketMenu.getMenuUid());

        if (menuInBasket != null)
            changeQuantity(memberUid, basketMenu.getMenuUid(), basketMenu.getQuantity() + menuInBasket.getQuantity());
        else
            basketMapper.insertBasketMenu(memberUid, basketMenu);
    }

    public BasketMenuDTO getMenuInBasket(long memberUid, long menuUid) {
        return basketMapper.selectBasketMenuByMemberUidAndMenuUid(memberUid, menuUid);
    }

    public void changeQuantity(long memberUid, long menuUid, int quantity) {
        if (quantity < 1)
            throw new IllegalArgumentException("수량은 1개 이상만 가능합니다.");

        basketMapper.updateBasketMenuQuantity(memberUid, menuUid, quantity);
    }

    public void deleteMenuInBasket(long memberUid, Long menuUid) {
        basketMapper.deleteMenuInBasket(memberUid, menuUid);
    }

    public void makeBasket(long memberUid, long restaurantUid) {
        basketMapper.insertBasket(memberUid, restaurantUid);
    }

    public BasketDTO getBasket(long memberUid) {
        return basketMapper.selectBasketByMemberUid(memberUid);
    }

    public void deleteBasket(long memberUid) {
        deleteMenuInBasket(memberUid, null);
        basketMapper.deleteBasket(memberUid);
    }

    public List<BasketMenuDTO> getAllMenuInBasket(long memberUid) {
        return basketMapper.selectAllMenuInBasket(memberUid);
    }
}
