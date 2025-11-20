package com.example.AdvancedUser.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registrations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstTimeAttendingCamp;

    @NotBlank
    @Column(name = "child_name")
    private String childName;

    @NotNull
    @Min(1)
    private Integer age;

    @NotBlank
    private String tcCenter;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    @NotNull
    private Zone zone;

    @NotBlank
    private String address;

    @NotBlank
    @Column(name = "parent_name")
    private String parentName;

    @NotBlank
    @Column(name = "parent_phone")
    private String parentPhone;

    @NotBlank
    private String allergy;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @NotNull
    private Payment payment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Registrations() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getFirstTimeAttendingCamp() {
        return firstTimeAttendingCamp;
    }

    public void setFirstTimeAttendingCamp(String firstTimeAttendingCamp) {
        this.firstTimeAttendingCamp = firstTimeAttendingCamp;
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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
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

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

