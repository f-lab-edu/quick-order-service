package com.quickorderservice.service.rider;

import com.quickorderservice.dto.rider.RiderDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.mapper.RiderMapper;
import com.quickorderservice.utiles.SHA256;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiderServiceTest {

    @Mock
    RiderMapper riderMapper;

    @InjectMocks
    RiderService riderService;

    @Test
    @DisplayName("정상적인 회원가입시 성공")
    public void joinRider() {
        RiderDTO rider = new RiderDTO(1L, "test", "1234", "test", "test", "test", null, null);

        when(riderMapper.insertRider(rider, SHA256.encBySha256(rider.getPassword()))).thenReturn(1);

        riderService.joinRider(rider);

        verify(riderMapper).insertRider(rider, SHA256.encBySha256(rider.getPassword()));
    }

    @Test
    @DisplayName("존재하는 아이디로 회원가입시 예외 발생")
    public void joinRiderWithDuplicatedId() {
        RiderDTO rider = new RiderDTO(1L, "test", "1234", "test", "test", "test", null, null);

        when(riderMapper.selectRiderByRiderId("test")).thenReturn(rider);

        assertThrows(DuplicatedIdException.class, () -> {
            riderService.joinRider(rider);
        });
    }

    @Test
    @DisplayName("존재하는 rider의 uid로 rider를 조회하면 성공")
    public void findRiderByUid() {
        RiderDTO rider = new RiderDTO(1L, "test", "1234", "test", "test", "test", null, null);

        when(riderMapper.selectRiderByRiderUid(1L)).thenReturn(rider);

        RiderDTO findRider = riderService.findRiderByUid(1L);

        verify(riderMapper).selectRiderByRiderUid(1L);
        assertThat(findRider).isEqualTo(rider);
    }

    @Test
    @DisplayName("존재하는 rider의 id와 pw로 rider를 조회하면 성공")
    public void findRiderByIdAndPassword() {
        RiderDTO rider = new RiderDTO(1L, "test", "1234", "test", "test", "test", null, null);

        when(riderMapper.selectRiderByIdAndPassword("test", SHA256.encBySha256(rider.getPassword()))).thenReturn(rider);

        RiderDTO findRider = riderService.findRiderByIdAndPassword("test", "1234");

        verify(riderMapper).selectRiderByIdAndPassword("test", SHA256.encBySha256(rider.getPassword()));
        assertThat(findRider).isEqualTo(rider);
    }

    @Test
    @DisplayName("존재하지 않는 id와 pw로 rider를 조회시 null을 반환한다. ")
    public void findRiderByWrongIdAndPassword() {
        when(riderMapper.selectRiderByIdAndPassword(any(), any())).thenReturn(null);

        RiderDTO findRider = riderService.findRiderByIdAndPassword("test", "1234");

        verify(riderMapper).selectRiderByIdAndPassword(any(), any());
        assertThat(findRider).isNull();
    }
}