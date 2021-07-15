package com.quickorderservice.controller;

import com.quickorderservice.annotation.OwnerId;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.enumdata.RestaurantCategory;
import com.quickorderservice.service.owner.OwnerService;
import com.quickorderservice.service.owner.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public void registerRestaurant(@OwnerId long ownerUid, @RequestBody RestaurantDTO restaurant) {
        restaurantService.registerRestaurant(ownerUid, restaurant);
    }

    @GetMapping("/owners")
    public List<RestaurantDTO> getRestaurantsByOwnerUid(@OwnerId long ownerUid) {
        return restaurantService.getRestaurantsByOwnerId(ownerUid);
    }

    @GetMapping()
    public List<RestaurantDTO> getRestaurants(
            @RequestParam(required = false) RestaurantCategory category,
            @RequestParam(required = false, defaultValue = "10") int maxsize,
            @RequestParam(required = false, defaultValue = "0") int start) {
        return restaurantService.getAllRestaurants(category, maxsize, start);
    }

}
