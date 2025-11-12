package com.example.AdvancedUser.service;

import com.example.AdvancedUser.config.PaystackConfig;
import com.example.AdvancedUser.dto.PaystackInitializeRequest;
import com.example.AdvancedUser.dto.PaystackInitializeResponse;
import com.example.AdvancedUser.dto.PaystackVerifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaystackService {

    @Autowired
    private PaystackConfig paystackConfig;

    @Autowired
    private RestTemplate restTemplate;

    public PaystackInitializeResponse initializePayment(PaystackInitializeRequest request) {
        String url = paystackConfig.getBaseUrl() + "/transaction/initialize";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(paystackConfig.getSecretKey());

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", request.getEmail());
        payload.put("amount", request.getAmount());
        payload.put("reference", request.getReference());
        payload.put("callback_url", request.getCallbackUrl());
        payload.put("metadata", request.getMetadata());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<PaystackInitializeResponse> response = restTemplate.postForEntity(
                url, entity, PaystackInitializeResponse.class
        );

        return response.getBody();
    }

    public PaystackVerifyResponse verifyPayment(String reference) {
        String url = paystackConfig.getBaseUrl() + "/transaction/verify/" + reference;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(paystackConfig.getSecretKey());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PaystackVerifyResponse> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, PaystackVerifyResponse.class
        );

        return response.getBody();
    }
}


