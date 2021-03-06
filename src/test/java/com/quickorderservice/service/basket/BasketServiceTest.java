package com.quickorderservice.service.basket;

import com.quickorderservice.dto.basket.BasketMenu;
import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.exception.BasketException;
import com.quickorderservice.service.member.MemberLoginService;
import com.quickorderservice.service.member.MemberService;
import com.quickorderservice.service.menu.MenuService;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BasketServiceTest {

    @Autowired
    BasketService basketService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberLoginService memberLoginService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    MenuService menuService;

    long memberUid;
    long ownerUid;
    long restaurantUid;
    long menuUid;

    @BeforeEach
    public void beforeEach() {
        MemberDTO member = new MemberDTO(null, "testMember", "1234", null, null, null, "분당구 불정로 6", 0, 0, null, null);
        memberService.joinMember(member);
        memberUid = memberService.findMemberById("testMember").getUid();
        memberLoginService.login("testMember", "1234");

        OwnerDTO owner = new OwnerDTO(null, "owner", "1234", null, null, null, null, null);
        ownerService.joinOwner(owner);
        ownerUid = ownerService.findOwnerById("owner").getUid();

        RestaurantDTO restaurant = new RestaurantDTO(null, ownerUid, null, null, "분당구 불정로 6", 0, 0, RestaurantCategory.KOREAN, null, null);
        restaurantService.registerRestaurant(ownerUid, restaurant);
        restaurantUid = restaurantService.getRestaurantsByOwnerId(ownerUid).get(0).getUid();

        MenuDTO menu = new MenuDTO(null, restaurantUid, null, 100, 10, null, null);
        menuService.registerMenu(ownerUid, restaurantUid, menu);
        menuUid = menuService.getAllMenusByRestaurant(restaurantUid).get(0).getUid();
    }

    @Test
    @DisplayName("정상적으로 장바구니에 메뉴를 담고 List로 반환 받을 수 있다.")
    public void putMenuInBasket() {
        BasketMenu basketMenu = new BasketMenu(menuUid, restaurantUid, memberUid, 1);

        basketService.putMenuInBasket(memberUid, basketMenu);

        List<BasketMenu> allBasketMenus = basketService.getAllBasketMenusByMemberUid(memberUid);

        Assertions.assertThat(allBasketMenus.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("다른 식당의 메뉴를 담으면 예외가 발생한다.")
    public void putMenuInBasketWithWrongMenu() {
        BasketMenu basketMenu = new BasketMenu(1, 1, memberUid, 1);
        BasketMenu basketMenu2 = new BasketMenu(2, 2, memberUid, 2);

        org.junit.jupiter.api.Assertions.assertThrows(BasketException.class, () -> {
            basketService.putMenuInBasket(memberUid, basketMenu);
            basketService.putMenuInBasket(memberUid, basketMenu2);
        });
    }

}