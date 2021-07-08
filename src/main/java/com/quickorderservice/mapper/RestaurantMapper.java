package com.quickorderservice.mapper;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    void insertRestaurant(@Param("ownerId") long ownerId, @Param("res") RestaurantDTO restaurant);

    List<RestaurantDTO> selectRestaurantsByOwnerId(Long ownerId);
}