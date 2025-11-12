package com.example.AdvancedUser.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PaystackConfig {

    @Value("${paystack.secret.key}")
    private String secretKey;

    @Value("${paystack.public.key}")
    private String publicKey;

    @Value("${paystack.base.url}")
    private String baseUrl;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}


