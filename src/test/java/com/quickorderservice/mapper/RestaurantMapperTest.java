package com.quickorderservice.mapper;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.dto.restaurant.RestaurantDTO;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.owner.OwnerLoginService;
import com.quickorderservice.service.owner.OwnerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class RestaurantMapperTest {

    @Autowired
    RestaurantMapper restaurantMapper;
    @Autowired
    OwnerService ownerService;
    @Autowired
    OwnerLoginService ownerLoginService;

    @Test
    @DisplayName("로그인 없이 가게 등록시 예외가 발생한다.")
    public void insertRestaurant() {
        OwnerDTO owner = new OwnerDTO(null, "testID", "1234", "test", "0000", "test@test.com", LocalDateTime.now(), LocalDateTime.now());
        ownerService.joinOwner(owner);
        ownerLoginService.login(owner.getOwnerId(), "1234");

        OwnerDTO findOwner = ownerService.findOwnerByUid(ownerLoginService.getLoginUid());

        RestaurantDTO restaurant = new RestaurantDTO(null, findOwner.getUid(), "test", "1234",null,
                LocalDateTime.now(), LocalDateTime.now());

        restaurantMapper.insertRestaurant(findOwner.getUid(), restaurant);
        List<RestaurantDTO> restaurants = restaurantMapper.selectRestaurantsByOwnerId(findOwner.getUid());

        Assertions.assertThat(restaurants.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Owner의 로그인 없이 등록하면 정상적으로 가게가 등록 된다.")
    public void insertRestaurantWithoutLogin() {
        OwnerDTO owner = new OwnerDTO(null, "testID", "1234", "test", "0000", "test@test.com", LocalDateTime.now(), LocalDateTime.now());
        ownerService.joinOwner(owner);

        org.junit.jupiter.api.Assertions.assertThrows(NeedLoginException.class, () -> {
            OwnerDTO findOwner = ownerService.findOwnerByUid(ownerLoginService.getLoginUid());

            RestaurantDTO restaurant = new RestaurantDTO(null, findOwner.getUid(), "test", "1234",null,
                    LocalDateTime.now(), LocalDateTime.now());

            restaurantMapper.insertRestaurant(findOwner.getUid(), restaurant);
        });
    }
}