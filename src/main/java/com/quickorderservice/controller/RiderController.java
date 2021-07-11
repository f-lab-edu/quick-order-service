package com.quickorderservice.controller;

import com.quickorderservice.dto.rider.RiderDTO;
import com.quickorderservice.service.rider.RiderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/riders")
@AllArgsConstructor
public class RiderController {

    private final RiderService reRiderService;

    @PostMapping
    public void joinOwner(@RequestBody RiderDTO riderDTO) {
        reRiderService.joinRider(riderDTO);
    }
}
