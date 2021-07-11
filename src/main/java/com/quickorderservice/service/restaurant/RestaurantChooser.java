package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NeedChooseRestaurantException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Service
public class RestaurantChooser {

    private final HttpSession httpSession;
    private final String RESTAURANT_UID = "restaurantUid";
    private final RestaurantService restaurantService;

    public void chooseRestaurant(Long uid) {
        RestaurantDTO restaurantsByUid = restaurantService.getRestaurantsByUid(uid);

        if (restaurantsByUid == null)
            throw new IllegalArgumentException("존재하지 않는 식당입니다.");

        httpSession.setAttribute(RESTAURANT_UID, uid);
    }

    public RestaurantDTO getChosenRestaurant() {
        Long restaurantUid = (Long) httpSession.getAttribute(RESTAURANT_UID);

        if (restaurantUid == null)
            throw new NeedChooseRestaurantException("선택된 식당이 없습니다.");

        return restaurantService.getRestaurantsByUid(restaurantUid);
    }

}
