package com.example.AdvancedUser.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Min(1)
    private Integer numberOfRegistrants;

    @NotNull
    private Long zoneId;

    public PaymentRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumberOfRegistrants() {
        return numberOfRegistrants;
    }

    public void setNumberOfRegistrants(Integer numberOfRegistrants) {
        this.numberOfRegistrants = numberOfRegistrants;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }
}


