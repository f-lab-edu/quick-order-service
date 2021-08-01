package com.quickorderservice.mapper;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.utiles.geo.LatLonData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RestaurantMapper {

    void insertRestaurant(@Param("ownerId") long ownerId, @Param("res") RestaurantDTO restaurant, @Param("latLon") LatLonData latLonData);

    List<RestaurantDTO> selectRestaurantsByOwnerId(Long ownerId);

    List<RestaurantDTO> selectRestaurants(@Param("category") RestaurantCategory category, @Param("limit") int limit, @Param("offset") int offset);

    RestaurantDTO selectRestaurantsByUid(Long uid);

}

