package com.quickorderservice.service.owner;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.mapper.RestaurantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final OwnerService ownerService;

    public void registerRestaurant(String ownerId, RestaurantDTO restaurant) {
        OwnerDTO owner = ownerService.findOwnerById(ownerId);
        restaurant.setOwnerId(owner.getUid());
        restaurantMapper.insertRestaurant(restaurant);
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(String ownerId) {
        OwnerDTO owner = ownerService.findOwnerById(ownerId);
        return restaurantMapper.selectRestaurantsByOwnerId(owner.getUid());
    }
}
