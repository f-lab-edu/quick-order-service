package com.quickorderservice.service.owner;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.owner.NotFoundOwnerException;
import com.quickorderservice.mapper.OwnerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerMapper ownerMapper;

    public void joinOwner(OwnerDTO ownerDTO) {
        if (isExistOwner(ownerDTO.getOwnerId()))
            throw new DuplicatedIdException();

        ownerMapper.insertOwner(ownerDTO);
    }

    public OwnerDTO findOwnerById(String ownerId) {
        OwnerDTO findOwner = ownerMapper.selectOwnerById(ownerId);

        if (findOwner == null)
            throw new NotFoundOwnerException("존재하지 않는 회원 입니다.");

        return findOwner;
    }

    public List<OwnerDTO> findAllOwners() {
        return ownerMapper.selectAllOwners();
    }

    private boolean isExistOwner(String ownerId) {
        return ownerMapper.selectOwnerById(ownerId) != null;
    }
}
