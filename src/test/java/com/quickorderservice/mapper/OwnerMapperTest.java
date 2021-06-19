package com.quickorderservice.mapper;

import com.quickorderservice.dto.owner.OwnerDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OwnerMapperTest {

    @Autowired
    OwnerMapper ownerMapper;

    @Test
    @DisplayName("정상적으로 회원가입이 되면 회원수가 기존보다 1이 증가한다")
    public void joinOwner() {
        int ownersCountBeforeJoin = ownerMapper.selectAllOwners().size();

        OwnerDTO ownerDTO = new OwnerDTO(1L, "test", "1234", "test", "0000", "test@test.com", LocalDateTime.now(), LocalDateTime.now());
        ownerMapper.insertOwner(ownerDTO);

        int ownersCountAfterJoin = ownerMapper.selectAllOwners().size();

        Assertions.assertThat(ownersCountAfterJoin).isEqualTo(ownersCountBeforeJoin + 1);
    }

    @Test
    @DisplayName("이미 존재하는 아이디로 회원가입을 하면 예외를 던진다.")
    public void joinOwnerWithDuplicatedId() {
        OwnerDTO owner1 = new OwnerDTO(1L, "test", "1234", "test", "0000", "test@test.com", LocalDateTime.now(), LocalDateTime.now());
        ownerMapper.insertOwner(owner1);

        assertThrows(DuplicateKeyException.class, () -> {
            OwnerDTO owner2 = new OwnerDTO(1L, "test", "1234", "test", "0000", "test@test.com", LocalDateTime.now(), LocalDateTime.now());
            ownerMapper.insertOwner(owner2);
        });
    }

    @Test
    @DisplayName("정상적으로 회원을 조회하면 회원을 반환한다.")
    public void findOwner() {
        OwnerDTO owner = new OwnerDTO(1L, "test", "1234", "test", "0000", "test@test.com", LocalDateTime.now(), LocalDateTime.now());
        ownerMapper.insertOwner(owner);

        OwnerDTO findOwner = ownerMapper.selectOwnerById("test");
        Assertions.assertThat(owner).isEqualTo(findOwner);
    }

    @Test
    @DisplayName("존재하지 않는 회원을 조회하면 null을 반환한다.")
    public void findOwnerWithNotExistOwner() {
        OwnerDTO findOwner = ownerMapper.selectOwnerById("NoOwner");
        Assertions.assertThat(findOwner).isNull();
    }
}