package com.quickorderservice.controller;

import com.quickorderservice.dto.owner.OwnerDTO;
import com.quickorderservice.service.owner.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public void joinOwner(@RequestBody OwnerDTO ownerDTO) {
        ownerService.joinOwner(ownerDTO);
    }

    @GetMapping("/{ownerId}")
    public void findOwnerById(@PathVariable String ownerId) {
        ownerService.findOwnerById(ownerId);
    }
}
