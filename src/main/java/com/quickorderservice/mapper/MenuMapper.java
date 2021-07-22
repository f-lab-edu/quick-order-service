package com.quickorderservice.mapper;

import com.quickorderservice.dto.menu.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {

    MenuDTO selectMenuByUid(Long uid);

    List<MenuDTO> selectAllMenuByRestaurantId(@Param("restaurantUid") Long restaurantUid);

    int insertMenu(@Param("menu") MenuDTO menuDTO, @Param("restaurantUid") Long restaurantUid);

}
