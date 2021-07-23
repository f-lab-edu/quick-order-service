package com.quickorderservice.service.basket;

import com.quickorderservice.dto.basket.BasketDTO;
import com.quickorderservice.dto.basket.BasketMenuDTO;
import com.quickorderservice.mapper.BasketMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketService {

    private final BasketMapper basketMapper;

    /*
        1. 메뉴 담기
          1-1) 장바구니가 없다면 새로 생성해서 담음
          1-2) 장바구니가 존재한다면 동일 식당인지 확인
             1-2-1) 동일 식당이면 담음
               1-2-1-1) 이미 존재하는 메뉴라면 수량 변경
               1-2-1-2) 존재히자 않은 메뉴
             1-2-2) 동일 식당이 아니면 예외
        2. 메뉴 수량 수정 ( n>0)
        3. 메뉴 삭제


     */

    public void addMenuInBasket(long memberUid, BasketMenuDTO basketMenu) {
        BasketDTO basket = getBasket(memberUid);

        if (basket == null) {
            makeBasket(memberUid, basketMenu.getRestaurantUid());
            basket = getBasket(memberUid);
        }

        if (basket.getRestaurantUid() != basket.getRestaurantUid())
            throw new RuntimeException("다른 식당의 메뉴는 장바구니에 추가할 수 없습니다.");

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
        basketMapper.updateBasketMenuQuantity(memberUid, menuUid, quantity);
    }

    public void deleteMenuInBasket(long memberUid, long menuUid) {
        basketMapper.deleteMenuInBasket(memberUid, menuUid);
    }

    public void makeBasket(long memberUid, long restaurantUid) {
        basketMapper.insertBasket(memberUid, restaurantUid);
    }

    public BasketDTO getBasket(long memberUid) {
        return basketMapper.selectBasketByMemberUid(memberUid);
    }

    public void deleteBasket(long memberUid) {
        deleteAllMenuInBasket(memberUid);
        basketMapper.deleteBasket(memberUid);
    }

    public void deleteAllMenuInBasket(long memberUid) {
        basketMapper.deleteAllMenuInBasket(memberUid);
    }

    public List<BasketMenuDTO> getAllMenuInBasket(long memberUid) {
        return basketMapper.selectAllMenuInBasket(memberUid);
    }
}
