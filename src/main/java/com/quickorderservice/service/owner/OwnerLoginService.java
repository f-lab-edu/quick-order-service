package com.quickorderservice.service.owner;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.exception.auth.NeedLoginException;
import com.quickorderservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class OwnerLoginService implements LoginService {

    private final String OWNER_UID = "OwnerUid";
    private final OwnerService ownerService;
    private final HttpSession httpSession;

    public void login(String id, String password) {
        OwnerDTO loginOwner = ownerService.findOwnerByIdAndPassword(id, password);
        httpSession.setAttribute(OWNER_UID, loginOwner.getUid());
    }

    public void logout() {
        httpSession.removeAttribute(OWNER_UID);
    }

    public Long getLoginOwnerUid() {
        Long loginOwnerUid = (Long) httpSession.getAttribute(OWNER_UID);

        if(loginOwnerUid == null)
            throw new NeedLoginException("로그인이 필요합니다.");

        return loginOwnerUid;
    }

}
