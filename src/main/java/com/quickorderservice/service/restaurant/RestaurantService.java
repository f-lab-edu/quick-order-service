package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.mapper.RestaurantMapper;
import com.quickorderservice.utiles.geo.GeoData;
import com.quickorderservice.utiles.geo.Geocoding;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final Geocoding geocoding;

    public void registerRestaurant(Long ownerUid, RestaurantDTO restaurant) {
        GeoData geoDataByAddress = geocoding.getGeoDataByAddress(restaurant.getAddress());
        GeoData.Address[] addresses = geoDataByAddress.getAddresses();
        double lat = addresses[0].getX();
        double lon = addresses[0].getY();

        restaurantMapper.insertRestaurant(ownerUid, restaurant, lat, lon);
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
