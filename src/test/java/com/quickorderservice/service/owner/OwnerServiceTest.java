package com.quickorderservice.service.owner;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.owner.NotFoundOwnerException;
import com.quickorderservice.mapper.OwnerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    OwnerMapper ownerMapper;

    @InjectMocks
    OwnerService ownerService;

    @Test
    @DisplayName("정상적인 회원가입시 성공")
    public void joinOwner() {
        OwnerDTO owner = new OwnerDTO(1L, "test", "test", "test", "test", "test",
                LocalDateTime.now(), LocalDateTime.now());

        when(ownerMapper.insertOwner(owner)).thenReturn(1);

        ownerService.joinOwner(owner);

        verify(ownerMapper).insertOwner(owner);
    }

    @Test
    @DisplayName("존재하는 아이디로 회원가입시 예외 발생")
    public void joinOwnerWithDuplicatedId() {
        OwnerDTO owner = new OwnerDTO(1L, "test", "test", "test", "test", "test",
                LocalDateTime.now(), LocalDateTime.now());

        when(ownerMapper.selectOwnerById("test")).thenReturn(owner);

        assertThrows(DuplicatedIdException.class, () -> {
            ownerService.joinOwner(owner);
        });
    }

    @Test
    @DisplayName("존재하는 owner 아이디를 조회하면 정상적으로 owner 객체 반환 ")
    public void findOwnerById() {
        OwnerDTO owner = new OwnerDTO(1L, "test", "test", "test", "test", "test",
                LocalDateTime.now(), LocalDateTime.now());

        when(ownerMapper.selectOwnerById("test")).thenReturn(owner);

        OwnerDTO findOwner = ownerService.findOwnerById("test");

        verify(ownerMapper).selectOwnerById("test");

        assertThat(findOwner).isEqualTo(owner);
    }

    @Test
    @DisplayName("존재하지 않은 owner 아이디 조회하면 NotFoundOwnerException 예외 발생")
    public void findOwnerByIdWithNotExist() {
        when(ownerMapper.selectOwnerById("test")).thenReturn(null);

        Assertions.assertThrows(NotFoundOwnerException.class, () -> {
            OwnerDTO findOwner = ownerService.findOwnerById("test");
        });

        verify(ownerMapper).selectOwnerById("test");
    }
}