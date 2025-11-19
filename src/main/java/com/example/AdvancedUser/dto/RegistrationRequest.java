package com.example.AdvancedUser.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegistrationRequest {
    @NotBlank
    private String firstTimeAttendingCamp;
    @NotBlank
    private String childName;

    @NotNull
    @Min(1)
    private Integer age;

    @NotBlank
    private String tcCenter;

    @NotNull
    private Long zoneId;

    @NotBlank
    private String address;

    @NotBlank
    private String parentName;

    @NotBlank
    private String parentPhone;

    @NotBlank
    private String allergy;

    @NotNull
    private String paymentReference;
    private boolean consentGiven;

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }

    public RegistrationRequest() {
    }

    public String getFirstTimeAttendingCamp() {
        return firstTimeAttendingCamp;
    }

    public void setFirstTimeAttendingCamp(String firstTimeAttendingCamp) {
        this.firstTimeAttendingCamp = firstTimeAttendingCamp;
    }

    // Getters and Setters
    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTcCenter() {
        return tcCenter;
    }

    public void setTcCenter(String tcCenter) {
        this.tcCenter = tcCenter;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
}


