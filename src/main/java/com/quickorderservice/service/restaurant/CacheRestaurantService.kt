package com.quickorderservice.service.restaurant

import com.quickorderservice.dto.restaurant.RestaurantDTO
import com.quickorderservice.enumdata.RestaurantCategory
import com.quickorderservice.mapper.RestaurantMapper
import com.quickorderservice.utiles.geo.Geocoding
import com.quickorderservice.utiles.geo.LatLonData
import lombok.AllArgsConstructor
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
@Primary
open class CacheRestaurantService(private val restaurantMapper: RestaurantMapper, private val geocoding: Geocoding) : IRestaurantService {

    override fun registerRestaurant(ownerUid: Long, restaurant: RestaurantDTO) {
        val latLon: LatLonData = geocoding.getLatLon(null)
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