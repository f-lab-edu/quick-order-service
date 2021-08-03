package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.mapper.RestaurantMapper;
import com.quickorderservice.utiles.geo.Geocoding;
import com.quickorderservice.utiles.geo.LatLonData;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final Geocoding geocoding;

    @Override
    public void registerRestaurant(long ownerUid, @NotNull RestaurantDTO restaurant) {
        LatLonData latLon = geocoding.getLatLon(restaurant.getAddress());
        restaurantMapper.insertRestaurant(ownerUid, restaurant, latLon);
    }

    @NotNull
    @Override
    public List<RestaurantDTO> getRestaurantsByOwnerId(long ownerUid) {
        return restaurantMapper.selectRestaurantsByOwnerId(ownerUid);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants(RestaurantCategory category, int pageSize, int page) {
        return restaurantMapper.selectRestaurants(category, pageSize, page * pageSize + 1);
    }

    @NotNull
    @Override
    public RestaurantDTO getRestaurantsByUid(long uid) {
        return restaurantMapper.selectRestaurantsByUid(uid);
    }
}
