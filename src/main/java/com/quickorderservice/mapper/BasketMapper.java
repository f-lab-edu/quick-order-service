package com.quickorderservice.mapper;

import com.quickorderservice.dto.basket.BasketDTO;
import com.quickorderservice.dto.basket.BasketMenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface BasketMapper {

    int insertBasket(@Param("memberUid") long memberUid, @Param("restaurantUid") long restaurantUid);

    int deleteBasket(long memberUid);

    BasketDTO selectBasketByMemberUid(long memberUid);

    int insertBasketMenu(@Param("memberUid") long memberUid, @Param("basketMenu") BasketMenuDTO basketMenu);

    int deleteMenuInBasket(long memberUid, long menuUid);

    int deleteAllMenuInBasket(long memberUid);

    int updateBasketMenuQuantity(@Param("memberUid") long memberUid, @Param("menuUid") long menuUid, @Param("quantity") int quantity);

    List<BasketMenuDTO> selectAllMenuInBasket(long memberUid);

    BasketMenuDTO selectBasketMenuByMemberUidAndMenuUid(@Param("memberUid") long memberUid, @Param("menuUid") long menuUid);
}
