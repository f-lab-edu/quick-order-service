package com.quickorderservice.mapper;

import com.quickorderservice.dto.owner.OwnerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OwnerMapper {

    int insertOwner(OwnerDTO ownerDTO);

    OwnerDTO selectOwnerById(String ownerId);

    List<OwnerDTO> selectAllOwners();

    OwnerDTO selectOwnerByIdAndPassword(@Param("ownerId") String ownerId, @Param("password") String password);

    OwnerDTO selectOwnerByUid(Long uid);
}
