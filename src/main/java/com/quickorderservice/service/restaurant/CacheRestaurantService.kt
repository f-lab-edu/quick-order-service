package com.quickorderservice.service.restaurant

import com.quickorderservice.dto.restaurant.RestaurantDTO
import com.quickorderservice.enumdata.RestaurantCategory
import com.quickorderservice.mapper.RestaurantMapper
import com.quickorderservice.utiles.geo.Geocoding
import com.quickorderservice.utiles.geo.LatLonData
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
open class CacheRestaurantService(private val restaurantMapper: RestaurantMapper, private val geocoding: Geocoding) :
    RestaurantService {

    override fun registerRestaurant(ownerUid: Long, restaurant: RestaurantDTO) {
        val latLon: LatLonData = geocoding.getLatLon(restaurant.address)
        restaurantMapper.insertRestaurant(ownerUid, restaurant, latLon)
    }

    override fun getRestaurantsByOwnerId(ownerUid: Long): List<RestaurantDTO> {
        return restaurantMapper.selectRestaurantsByOwnerId(ownerUid)
    }

    override fun getAllRestaurants(category: RestaurantCategory?, pageSize: Int, page: Int): List<RestaurantDTO> {
        val restaurants = restaurantMapper.selectRestaurants(category, pageSize, page * pageSize)
        return restaurants
    }

    @Cacheable(key = "#uid", value = ["getRestaurantsByUid"])
    override fun getRestaurantsByUid(uid: Long): RestaurantDTO {
        return restaurantMapper.selectRestaurantsByUid(uid)
    }
}
