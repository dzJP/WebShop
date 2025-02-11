package com.example.webshoplabb.dto;

public class BankIdAuthRequest {
    private String personalNumber;

    public BankIdAuthRequest(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }
}
