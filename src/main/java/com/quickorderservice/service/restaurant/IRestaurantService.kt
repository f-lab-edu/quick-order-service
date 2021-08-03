package com.quickorderservice.service.restaurant

import com.quickorderservice.dto.restaurant.RestaurantDTO
import com.quickorderservice.enumdata.RestaurantCategory

open interface IRestaurantService {

    fun registerRestaurant(ownerUid: Long, restaurant: RestaurantDTO)

    fun getRestaurantsByOwnerId(ownerUid: Long): List<RestaurantDTO>

    fun getAllRestaurants(category : RestaurantCategory?, pageSize : Int, page : Int):List<RestaurantDTO>

    fun getRestaurantsByUid(uid : Long) :RestaurantDTO

}