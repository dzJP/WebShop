package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.BankIdAuthRequest;
import com.example.webshoplabb.dto.BankIdAuthResponse;
import com.example.webshoplabb.dto.BankIdStatusResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class BankIdClient {

    private final RestTemplate restTemplate;

    private final String baseUrl = "https://appapi2.test.bankid.com/rp/v5.1";

    public BankIdClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String authenticateUser(BankIdAuthRequest request) {
        String authenticateUrl = baseUrl + "/authenticate";

        // Perform the POST request
        BankIdAuthResponse authResponse = restTemplate.postForObject(authenticateUrl, request, BankIdAuthResponse.class);

        // Log the authentication response (check if autoStartToken is returned)
        System.out.println("Authentication Response: " + authResponse);

        // Return the orderRef (this could be used to check the status)
        if (authResponse != null && authResponse.getOrderRef() != null) {
            return authResponse.getOrderRef();
        } else {
            throw new RuntimeException("Failed to get autoStartToken. Please try again.");
        }
    }

    public BankIdStatusResponse checkAuthenticationStatus(String orderRef) {
        String statusUrl = baseUrl + "/status?orderRef=" + orderRef;

        // Perform the GET request to check status
        return restTemplate.getForObject(statusUrl, BankIdStatusResponse.class);
    }
}
