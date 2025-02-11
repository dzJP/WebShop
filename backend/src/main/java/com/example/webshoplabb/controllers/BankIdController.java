package com.example.webshoplabb.controllers;

import com.example.webshoplabb.services.BankIdService;
import com.example.webshoplabb.dto.BankIdStatusResponse;
import com.example.webshoplabb.dto.BankIdAuthResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bankid")
@CrossOrigin(origins = "http://localhost:8081")  // CORS setup for the frontend
public class BankIdController {

    private final BankIdService bankIdService;

    public BankIdController(BankIdService bankIdService) {
        this.bankIdService = bankIdService;
    }

    // Start authentication (POST)
    @PostMapping("/authenticate")
    // Removed @PreAuthorize to allow unauthenticated access
    public BankIdAuthResponse authenticateUser(@RequestBody String personalNumber) {
        // Start the authentication and get the orderRef
        String orderRef = bankIdService.startAuthentication(personalNumber);
        return new BankIdAuthResponse(orderRef);
    }

    // Check authentication status (GET)
    @GetMapping("/status")
    public BankIdStatusResponse checkAuthenticationStatus(@RequestParam String orderRef) {
        return bankIdService.getAuthenticationStatus(orderRef);
    }
}
