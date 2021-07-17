package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.menu.MenuDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.mapper.MenuMapper;
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

    public RestaurantDTO getRestaurantsByUid(Long uid) {
        return restaurantMapper.selectRestaurantsByUid(uid);
    }

}