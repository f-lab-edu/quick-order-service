package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.member.MemberDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.mapper.RestaurantMapper;
import com.quickorderservice.service.member.MemberService;
import com.quickorderservice.utiles.CalculatorDistance;
import com.quickorderservice.utiles.geo.Geocoding;
import com.quickorderservice.utiles.geo.LatLonData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantMapper restaurantMapper;
    private final Geocoding geocoding;
    private final MemberService memberService;

    public void registerRestaurant(Long ownerUid, RestaurantDTO restaurant) {
        LatLonData latLon = geocoding.getLatLon(restaurant.getAddress());
        restaurantMapper.insertRestaurant(ownerUid, restaurant, latLon);
    }

    public List<RestaurantDTO> getRestaurantsByOwnerId(Long ownerUid) {
        return restaurantMapper.selectRestaurantsByOwnerId(ownerUid);
    }

    public List<RestaurantDTO> getAllRestaurants(RestaurantCategory category, int pageSize, int page) {
        return restaurantMapper.selectRestaurants(category, pageSize, page * pageSize);
    }

    public List<RestaurantDTO> getAvailableDeliveryRestaurants(long memberUid, RestaurantCategory category, int pageSize, int page) {
        MemberDTO member = memberService.findMemberByUid(memberUid);
        List<RestaurantDTO> allRestaurants = getAllRestaurants(category, pageSize, page);
        return allRestaurants.stream().filter(res -> isAvailableDelivery(member, res)).collect(Collectors.toList());
    }

    public RestaurantDTO getRestaurantsByUid(Long uid) {
        return restaurantMapper.selectRestaurantsByUid(uid);
    }

    private boolean isAvailableDelivery(MemberDTO member, RestaurantDTO res) {
        double distanceByKM = CalculatorDistance.distance(member.getLat(), member.getLon(), res.getLat(), res.getLon());

        if (distanceByKM <= res.getDeliveryRange()) return true;
        else return false;
    }

}
