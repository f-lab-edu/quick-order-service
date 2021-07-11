package com.quickorderservice.service.restaurant;

import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NeedChooseRestaurantException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantChooserTest {

    @Mock
    HttpSession httpSession;

    @Mock
    RestaurantService restaurantService;

    @InjectMocks
    RestaurantChooser restaurantChooser;

    final String RESTAURANT_UID = "restaurantUid";

    @Test
    @DisplayName("존재하는 Restaurant의 uid로 선택을 하면 정상 처리 된다.")
    public void chooseRestaurant() {
        RestaurantDTO restaurant = new RestaurantDTO(1L, null, "food", "000", null, null);

        when(restaurantService.getRestaurantsByUid(1L)).thenReturn(restaurant);

        restaurantChooser.chooseRestaurant(1L);

        verify(restaurantService).getRestaurantsByUid(1L);
        verify(httpSession).setAttribute(RESTAURANT_UID, 1L);
    }

    @Test
    @DisplayName("존재하지 않은 Restaurant의 uid로 선택을 예외가 발생한다.")
    public void chooseRestaurantWithWongUid() {
        when(restaurantService.getRestaurantsByUid(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            restaurantChooser.chooseRestaurant(1L);
        });
    }

    @Test
    @DisplayName("식당은 선택 했었다면 선택된 식당 조회시 식당을 반환한다.")
    public void getChosenRestaurant() {
        RestaurantDTO restaurant = new RestaurantDTO(1L, null, "food", "000", null, null);

        when(httpSession.getAttribute(RESTAURANT_UID)).thenReturn(1L);
        when(restaurantService.getRestaurantsByUid(1L)).thenReturn(restaurant);

        RestaurantDTO chosenRestaurant = restaurantChooser.getChosenRestaurant();

        verify(restaurantService).getRestaurantsByUid(1L);
        verify(httpSession).getAttribute(RESTAURANT_UID);

        assertThat(chosenRestaurant).isEqualTo(restaurant);
    }

    @Test
    @DisplayName("식당을 선택하지 않았다면 식당 조회시 예외가 발생한다..")
    public void getChosenRestaurantWithoutChoose() {

        when(httpSession.getAttribute(RESTAURANT_UID)).thenReturn(null);

        assertThrows(NeedChooseRestaurantException.class, () -> {
            RestaurantDTO chosenRestaurant = restaurantChooser.getChosenRestaurant();
        });

        verify(httpSession).getAttribute(RESTAURANT_UID);
    }

}