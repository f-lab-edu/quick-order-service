package com.quickorderservice.service.menu

import com.quickorderservice.dto.menu.MenuDTO
import com.quickorderservice.exception.NotFoundIdException
import com.quickorderservice.exception.RegisterException
import com.quickorderservice.mapper.MenuMapper
import com.quickorderservice.service.restaurant.RestaurantService
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
open class CacheMenuService(
    private val menuMapper: MenuMapper,
    private val restaurantService: RestaurantService
) : MenuService {

    @Cacheable(key = "#restaurantUid", value = ["getAllMenusByRestaurant"])
    override fun getAllMenusByRestaurant(restaurantUid: Long): List<MenuDTO> {
        return menuMapper.selectAllMenuByRestaurantId(restaurantUid)
    }

    @Cacheable(key = "{#restaurantUid, #menuUid}", value = ["getMenuByUid"])
    override fun getMenuByUid(restaurantUid: Long, menuUid: Long): MenuDTO {
        val menu = menuMapper.selectMenuByUid(menuUid)

        if (menu == null || menu.restaurantId != restaurantUid)
            throw NotFoundIdException("존재하지 않는 메뉴 입니다.")

        return menuMapper.selectMenuByUid(menuUid)
    }

    override fun registerMenu(ownerUid: Long, restaurantUid: Long, menu: MenuDTO) {
        require(isMatchedOwnerAndRestaurant(ownerUid, restaurantUid)) { "잘못된 식당에 접근하였습니다." }

        val result = menuMapper.insertMenu(menu, restaurantUid)

        if (result != 1) throw RegisterException("정상적으로 메뉴가 등록되지 않았습니다.")
    }

    private fun isMatchedOwnerAndRestaurant(ownerUid: Long, restaurantUid: Long): Boolean {
        val restaurant = restaurantService.getRestaurantsByUid(restaurantUid)
        return restaurant.ownerId == ownerUid
    }
}