package com.quickorderservice.service.menu

import com.quickorderservice.dto.menu.MenuDTO

interface IMenuService {

    fun getAllMenusByRestaurant(restaurantUid: Long): List<MenuDTO>

    fun getMenuByUid(restaurantUid: Long, menuUid: Long): MenuDTO

    fun registerMenu(ownerUid: Long, restaurantUid: Long, menu: MenuDTO)
}