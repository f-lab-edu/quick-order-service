package com.quickorderservice.service.rider;

import com.quickorderservice.dto.rider.RiderDTO;
import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class RiderLoginService implements LoginService {

    private final String RIDER_UID = "RiderUid";
    private final HttpSession httpSession;
    private final RiderService riderService;

    @Override
    public void login(String id, String password) {
        RiderDTO rider = riderService.findRiderByIdAndPassword(id, password);
        httpSession.setAttribute(RIDER_UID, rider.getUid());
    }

    @Override
    public void logout() {
        httpSession.removeAttribute(RIDER_UID);
    }

    @Override
    public Long getLoginUid() {
        Long loginRiderUid = (Long) httpSession.getAttribute(RIDER_UID);

        if (loginRiderUid == null)
            throw new NeedLoginException("로그인이 필요합니다.");

        return loginRiderUid;
    }
}
