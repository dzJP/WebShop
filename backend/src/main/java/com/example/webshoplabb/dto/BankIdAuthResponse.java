package com.example.webshoplabb.dto;

public class BankIdAuthResponse {
    private String orderRef;

    public BankIdAuthResponse(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }
}
