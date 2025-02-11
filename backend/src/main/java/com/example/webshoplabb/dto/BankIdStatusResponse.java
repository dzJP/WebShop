package com.example.webshoplabb.dto;

public class BankIdStatusResponse {
    private String status;

    public BankIdStatusResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
