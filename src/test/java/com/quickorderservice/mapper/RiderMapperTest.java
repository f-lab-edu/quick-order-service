package com.quickorderservice.mapper;

import com.quickorderservice.dto.rider.RiderDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RiderMapperTest {

    @Autowired RiderMapper riderMapper;

    @Test
    @DisplayName("정상적으로 회원가입이 되면 1을 반환한다.")
    public void insertRider() {
        RiderDTO riderDTO = new RiderDTO(null, "rider", "1234","nm", "000-0000-0000", "rider@a.com", null, null);
        int result = riderMapper.insertRider(riderDTO);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("id와 password가 일치하는 Rider가 있다면 RiderDTO를 반환한다.")
    public void selectRiderByIdAndPassword() {
        RiderDTO riderDTO = new RiderDTO(null, "rider", "1234","nm", "000-0000-0000", "rider@a.com", null, null);
        riderMapper.insertRider(riderDTO);

        RiderDTO findRider = riderMapper.selectRiderByIdAndPassword("rider", "1234");
        Assertions.assertThat(findRider.getRiderId()).isEqualTo("rider");
        Assertions.assertThat(findRider.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("id와 password가 일치하는 Rider가 없다면 예외 반환")
    public void selectRiderByWrongIdAndPassword() {
        RiderDTO findRider = riderMapper.selectRiderByIdAndPassword("rider", "1234");
        Assertions.assertThat(findRider).isNull();
    }

}