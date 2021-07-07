package com.quickorderservice.service.owner;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class OwnerLoginService implements LoginService {

    private final String OWNER_ID = "OwnerId";
    private final OwnerService ownerService;
    private final HttpSession httpSession;

    public void login(String id, String password) {
        OwnerDTO loginMember = ownerService.findOwnerByIdAndPassword(id, password);
        httpSession.setAttribute(OWNER_ID, id);
    }

    public void logout() {
        httpSession.removeAttribute(OWNER_ID);
    }

    public String getLoginId() {
        String userId = (String) httpSession.getAttribute(OWNER_ID);
        return (String) httpSession.getAttribute(OWNER_ID);
    }

}
