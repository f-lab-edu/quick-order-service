package com.quickorderservice.service.rider;

import com.quickorderservice.dto.rider.RiderDTO;
import com.quickorderservice.exception.DuplicatedIdException;
import com.quickorderservice.exception.FailMapperException;
import com.quickorderservice.mapper.RiderMapper;
import com.quickorderservice.utiles.SHA256;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RiderService {

    private final RiderMapper riderMapper;

    public void joinRider(RiderDTO riderDTO) {
        if (isExistRider(riderDTO.getRiderId()))
            throw new DuplicatedIdException();

        int result = riderMapper.insertRider(riderDTO, SHA256.encBySha256(riderDTO.getPassword()));

        if (result != 1)
            throw new FailMapperException("회원 가입이 실패하였습니다.");
    }

    public RiderDTO findRiderByUid(Long uid) {
        RiderDTO findRider = riderMapper.selectRiderByRiderUid(uid);
        return findRider;
    }

    public RiderDTO findRiderByIdAndPassword(String riderId, String password) {
        RiderDTO riderDTO = riderMapper.selectRiderByIdAndPassword(riderId, SHA256.encBySha256(password));
        return riderDTO;
    }

    private boolean isExistRider(String riderId) {
        return riderMapper.selectRiderByRiderId(riderId) != null;
    }
}
