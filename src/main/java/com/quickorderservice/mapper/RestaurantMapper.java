package com.quickorderservice.mapper;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    void insertRestaurant(RestaurantDTO restaurant);

    List<RestaurantDTO> selectRestaurantsByOwnerId(Long ownerId);
}
