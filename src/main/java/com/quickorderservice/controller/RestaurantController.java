package com.quickorderservice.controller;

import com.quickorderservice.annotation.OwnerId;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.service.owner.OwnerService;
import com.quickorderservice.service.restaurant.RestaurantChooser;
import com.quickorderservice.service.restaurant.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantChooser restaurantChooser;

    @PostMapping
    public void registerRestaurant(@OwnerId long ownerUid, @RequestBody RestaurantDTO restaurant) {
        restaurantService.registerRestaurant(ownerUid, restaurant);
    }

    @GetMapping
    public List<RestaurantDTO> getRestaurantsByOwnerUid(@OwnerId long ownerUid) {
        return restaurantService.getRestaurantsByOwnerId(ownerUid);
    }

    @PostMapping("/choose")
    public void chooseRestaurant(long restaurantUid) {
        restaurantChooser.chooseRestaurant(restaurantUid);
    }

}
