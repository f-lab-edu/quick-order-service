package com.quickorderservice.service.owner;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.mapper.RestaurantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;

    public void registerRestaurant(Long ownerUid, RestaurantDTO restaurant) {
        restaurant.setOwnerId(ownerUid);
        restaurantMapper.insertRestaurant(ownerUid, restaurant);
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerUid) {
        return restaurantMapper.selectRestaurantsByOwnerId(ownerUid);
    }
}
