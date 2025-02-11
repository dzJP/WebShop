package com.example.webshoplabb.services;

import com.example.webshoplabb.dto.BankIdAuthRequest;
import com.example.webshoplabb.dto.BankIdStatusResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BankIdService {

    private static final Logger logger = LoggerFactory.getLogger(BankIdService.class);
    private final BankIdClient bankIdClient;

    public BankIdService(BankIdClient bankIdClient) {
        this.bankIdClient = bankIdClient;
    }

    public String startAuthentication(String personalNumber) {
        try {
            logger.info("Starting BankID authentication for personal number: {}", personalNumber);

            // Request to BankID
            BankIdAuthRequest request = new BankIdAuthRequest(personalNumber);
            String orderRef = bankIdClient.authenticateUser(request);

            logger.info("Authentication started successfully, order reference: {}", orderRef);
            return orderRef;
        } catch (Exception e) {
            logger.error("Error during BankID authentication: {}", e.getMessage());
            throw new RuntimeException("BankID authentication failed", e);
        }
    }

    public BankIdStatusResponse getAuthenticationStatus(String orderRef) {
        try {
            logger.info("Checking authentication status for order reference: {}", orderRef);

            // Request to BankID for status
            BankIdStatusResponse status = bankIdClient.checkAuthenticationStatus(orderRef);

            logger.info("Authentication status for order reference {}: {}", orderRef, status);
            return status;
        } catch (Exception e) {
            logger.error("Error checking BankID authentication status: {}", e.getMessage());
            throw new RuntimeException("Failed to check authentication status", e);
        }
    }
}
