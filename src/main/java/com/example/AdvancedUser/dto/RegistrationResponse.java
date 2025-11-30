package com.example.AdvancedUser.dto;

import com.example.AdvancedUser.model.PaymentStatus;

import java.time.LocalDateTime;

public class RegistrationResponse {
    private Long id;
    private String firstTimeAttendingCamp;
    private String registrationType;
    private String childName;
    private Integer age;
    private String tcCenter;
    private String zoneName;
    private Long zoneId;
    private String address;
    private String parentName;
    private String parentPhone;
    private String allergy;
    private String paymentEmail;
    private String paymentReference;
    private PaymentStatus paymentStatus;
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime paymentCreatedAt;

    public RegistrationResponse() {
    }


    // Getters and Setters

    public String getFirstTimeAttendingCamp() {
        return firstTimeAttendingCamp;
    }

    public void setFirstTimeAttendingCamp(String firstTimeAttendingCamp) {
        this.firstTimeAttendingCamp = firstTimeAttendingCamp;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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

    public String getPaymentEmail() {
        return paymentEmail;
    }

    public void setPaymentEmail(String paymentEmail) {
        this.paymentEmail = paymentEmail;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaymentCreatedAt() {
        return paymentCreatedAt;
    }

    public void setPaymentCreatedAt(LocalDateTime paymentCreatedAt) {
        this.paymentCreatedAt = paymentCreatedAt;
    }
}


