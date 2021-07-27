package com.quickorderservice.controller;

import com.quickorderservice.dto.rider.RiderDTO;
import com.quickorderservice.service.rider.RiderLoginService;
import com.quickorderservice.service.rider.RiderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/riders")
@AllArgsConstructor
public class RiderController {

    private final RiderService reRiderService;
    private final RiderLoginService riderLoginService;

    @PostMapping
    public void joinRider(@RequestBody RiderDTO riderDTO) {
        reRiderService.joinRider(riderDTO);
    }

    @PostMapping("/login")
    public void riderLogin(String id, String password) {
        riderLoginService.login(id, password);
    }

    @PostMapping("/logout")
    public void riderLogout() {
        riderLoginService.logout();
    }
    
}
