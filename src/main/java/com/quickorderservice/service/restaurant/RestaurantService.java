package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.mapper.RestaurantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;

    public void registerRestaurant(Long ownerUid, RestaurantDTO restaurant) {
        restaurantMapper.insertRestaurant(ownerUid, restaurant);
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerUid) {
        return restaurantMapper.selectRestaurantsByOwnerId(ownerUid);
    }

    public List<RestaurantDTO> getAllRestaurants(RestaurantCategory category, int pageSize, int page) {
        return restaurantMapper.selectRestaurants(category, pageSize, page * pageSize);
    }

    public RestaurantDTO getRestaurantsByUid(Long uid) {
        return restaurantMapper.selectRestaurantsByUid(uid);
    }

}
