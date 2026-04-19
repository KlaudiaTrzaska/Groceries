package org.example.controllers;

import org.example.services.LoyaltyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ClientController {

    LoyaltyService loyaltyService;

    public ClientController(LoyaltyService loyaltyService) {
        this.loyaltyService = loyaltyService;
    }

    @GetMapping("/addClient")
    public String addClient(@RequestParam String name) {
        loyaltyService.addClient(name);
        return "Client " + name + " added!";
    }

    @GetMapping(value = "/points/{phoneNumber}")
    public String checkClientPoints(@PathVariable String phoneNumber) {
        return loyaltyService.checkPointsForClient(phoneNumber);
    }
}
