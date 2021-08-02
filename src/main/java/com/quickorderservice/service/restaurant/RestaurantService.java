package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.mapper.RestaurantMapper;
import com.quickorderservice.utiles.geo.Geocoding;
import com.quickorderservice.utiles.geo.LatLonData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final Geocoding geocoding;

    public void registerRestaurant(Long ownerUid, RestaurantDTO restaurant) {
        LatLonData latLon = geocoding.getLatLon(restaurant.getAddress());
        restaurantMapper.insertRestaurant(ownerUid, restaurant, latLon);
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerUid) {
        return restaurantMapper.selectRestaurantsByOwnerId(ownerUid);
    }

    public List<RestaurantDTO> getAllRestaurants(RestaurantCategory category, int pageSize, int page) {
        return restaurantMapper.selectRestaurants(category, pageSize, page * pageSize + 1);
    }

    public RestaurantDTO getRestaurantsByUid(Long uid) {
        return restaurantMapper.selectRestaurantsByUid(uid);
    }

}
