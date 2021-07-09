package com.quickorderservice.controller;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.service.LoginService;
import com.quickorderservice.service.owner.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final LoginService ownerLoginService;

    @PostMapping
    public void joinOwner(@RequestBody OwnerDTO ownerDTO) {
        ownerService.joinOwner(ownerDTO);
    }

    @PostMapping("/login")
    public void ownerLogin(String ownerId, String password) {
        ownerLoginService.login(ownerId, password);
    }

    @PostMapping("/logout")
    public void logout() {
        ownerLoginService.logout();
    }
}
