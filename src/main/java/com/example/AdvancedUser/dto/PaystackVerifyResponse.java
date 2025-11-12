package com.example.AdvancedUser.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackVerifyResponse {
    private boolean status;
    private String message;

    @JsonProperty("data")
    private PaystackVerifyData data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaystackVerifyData getData() {
        return data;
    }

    public void setData(PaystackVerifyData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PaystackVerifyData {
        private String reference;
        private Integer amount;
        private String status;

        @JsonProperty("gateway_response")
        private String gatewayResponse;

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGatewayResponse() {
            return gatewayResponse;
        }

        public void setGatewayResponse(String gatewayResponse) {
            this.gatewayResponse = gatewayResponse;
        }
    }
}


