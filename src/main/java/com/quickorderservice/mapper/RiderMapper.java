package com.quickorderservice.mapper;

import com.quickorderservice.dto.rider.RiderDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RiderMapper {

    int insertRider(RiderDTO riderDTO);

    RiderDTO selectRiderByIdAndPassword(@Param("riderId") String riderId, @Param("password") String password);

    RiderDTO selectRiderByRiderUid(Long uid);
}
