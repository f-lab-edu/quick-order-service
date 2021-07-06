package com.quickorderservice.service.owner;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.NotFoundIdException;
import com.quickorderservice.mapper.OwnerMapper;
import com.quickorderservice.utiles.SHA256;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerMapper ownerMapper;

    public void joinOwner(OwnerDTO ownerDTO) {
        if (isExistOwner(ownerDTO.getOwnerId()))
            throw new DuplicatedIdException();

        ownerDTO.setPassword(SHA256.encBySha256(ownerDTO.getPassword()));

        ownerMapper.insertOwner(ownerDTO);
    }

    public OwnerDTO findOwnerById(String ownerId) {
        OwnerDTO findOwner = ownerMapper.selectOwnerById(ownerId);

        if (findOwner == null)
            throw new NotFoundIdException("존재하지 않는 회원 입니다.");

        return findOwner;
    }

    public OwnerDTO findOwnerByUid(Long uid) {
        OwnerDTO findOwner = ownerMapper.selectOwnerByUid(uid);

        if (findOwner == null)
            throw new NotFoundIdException("존재하지 않는 회원 입니다.");

        return findOwner;
    }

    public OwnerDTO findOwnerByIdAndPassword(String ownerId, String password) {
        OwnerDTO owner = ownerMapper.selectOwnerByIdAndPassword(ownerId, SHA256.encBySha256(password));

        if (owner == null)
            throw new NotFoundIdException("아이디 혹은 비밀번호가 잘못되었습니다.");

        return owner;
    }

    private boolean isExistOwner(String ownerId) {
        return ownerMapper.selectOwnerById(ownerId) != null;
    }
}
