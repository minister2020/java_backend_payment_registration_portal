package com.example.AdvancedUser.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "zones")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    @Column(name = "account_number")
    private String accountNumber;

    @NotBlank
    @Column(name = "bank_code")
    private String bankCode;

    @NotBlank
    @Column(name = "bank_name")
    private String bankName;

    @NotNull
    @Column(name = "amount_per_registrant")
    private Double amountPerRegistrant;

    @Column(name = "paystack_recipient_code")
    private String paystackRecipientCode;

    public Zone() {
    }

    public Zone(String name, String accountNumber, String bankCode, String bankName, Double amountPerRegistrant) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.amountPerRegistrant = amountPerRegistrant;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getAmountPerRegistrant() {
        return amountPerRegistrant;
    }

    public void setAmountPerRegistrant(Double amountPerRegistrant) {
        this.amountPerRegistrant = amountPerRegistrant;
    }

    public String getPaystackRecipientCode() {
        return paystackRecipientCode;
    }

    public void setPaystackRecipientCode(String paystackRecipientCode) {
        this.paystackRecipientCode = paystackRecipientCode;
    }
}