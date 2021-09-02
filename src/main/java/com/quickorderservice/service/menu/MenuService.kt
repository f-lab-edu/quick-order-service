package com.quickorderservice.service.menu

import com.quickorderservice.dto.menu.MenuDTO

interface MenuService {

    fun getAllMenusByRestaurant(restaurantUid: Long): List<MenuDTO>

    fun getMenuByUid(restaurantUid: Long, menuUid: Long): MenuDTO

    fun registerMenu(ownerUid: Long, restaurantUid: Long, menu: MenuDTO)

    fun getMenuUidAndRestaurantUid(menuUid: Long, restaurantUid: Long): MenuDTO?
}
