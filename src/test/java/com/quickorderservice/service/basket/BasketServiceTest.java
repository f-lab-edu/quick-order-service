package com.quickorderservice.service.basket;

import com.quickorderservice.dto.basket.BasketMenu;
import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.exception.BasketException;
import com.quickorderservice.service.member.MemberLoginService;
import com.quickorderservice.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    long memberUid;

    @BeforeEach
    public void beforeEach() {
        MemberDTO member = new MemberDTO(null, "testMember", "1234", null, null, null, null, null, null);
        memberService.joinMember(member);
        memberUid = memberService.findMemberById("testMember").getUid();
        memberLoginService.login("testMember", "1234");
    }

    @Test
    @DisplayName("정상적으로 장바구니에 메뉴를 담고 map으로 반환 받을 수 있다.")
    public void putMenuInBasket() {
        BasketMenu basketMenu = new BasketMenu(1,1,memberUid,1);
        BasketMenu basketMenu2 = new BasketMenu(2,1,memberUid,2);

        basketService.putMenuInBasket(memberUid, basketMenu);
        basketService.putMenuInBasket(memberUid, basketMenu2);

        Map<Long, BasketMenu> allBasketMenus = basketService.getAllBasketMenusByMemberUid(memberUid);

        Assertions.assertThat(allBasketMenus.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("다른 식당의 메뉴를 담으면 예외가 발생한다.")
    public void putMenuInBasketWithWrongMenu() {
        BasketMenu basketMenu = new BasketMenu(1,1,memberUid,1);
        BasketMenu basketMenu2 = new BasketMenu(2,2,memberUid,2);

        org.junit.jupiter.api.Assertions.assertThrows(BasketException.class,()->{
            basketService.putMenuInBasket(memberUid, basketMenu);
            basketService.putMenuInBasket(memberUid, basketMenu2);
        });
    }

}