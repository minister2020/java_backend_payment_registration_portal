package com.example.AdvancedUser.service;

import com.example.AdvancedUser.config.PaystackConfig;
import com.example.AdvancedUser.dto.PaymentRequest;
import com.example.AdvancedUser.dto.PaystackInitializeRequest;
import com.example.AdvancedUser.dto.PaystackInitializeResponse;
import com.example.AdvancedUser.model.Payment;
import com.example.AdvancedUser.model.PaymentStatus;
import com.example.AdvancedUser.model.Zone;
import com.example.AdvancedUser.repository.PaymentRepository;
import com.example.AdvancedUser.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    @Value("${paystack.secret.key}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    @Autowired
    private PaystackConfig paystackConfig;


    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private PaystackService paystackService;

    @Autowired
    private SettlementService settlementService;

    @Value("${paystack.callback.url}")
    private String callbackUrl;

    @Value("${paystack.enable.auto.settlement:false}")
    private boolean enableAutoSettlement;

    public PaystackInitializeResponse initializePayment(PaymentRequest request) {
        // Get zone and calculate total amount
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        Double totalAmount = zone.getAmountPerRegistrant() * request.getNumberOfRegistrants();
        Integer amountInKobo = (int) (totalAmount * 100); // Convert to kobo (Paystack uses kobo)

        // Create payment record
        Payment payment = new Payment();
        payment.setEmail(request.getEmail());
        payment.setNumberOfRegistrants(request.getNumberOfRegistrants());
        payment.setTotalAmount(totalAmount);
        payment.setZone(zone);
        payment.setStatus(PaymentStatus.PENDING);

        String reference = UUID.randomUUID().toString();
        payment.setPaystackReference(reference);

        payment = paymentRepository.save(payment);

        // Initialize Paystack payment
        PaystackInitializeRequest paystackRequest = new PaystackInitializeRequest();
        paystackRequest.setEmail(request.getEmail());
        paystackRequest.setAmount(amountInKobo);
        paystackRequest.setReference(reference);
        paystackRequest.setCallbackUrl(callbackUrl);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("payment_id", payment.getId());
        metadata.put("zone_id", zone.getId());
        metadata.put("number_of_registrants", request.getNumberOfRegistrants());
        paystackRequest.setMetadata(metadata);

        return paystackService.initializePayment(paystackRequest);
    }

    public Payment verifyAndUpdatePayment(String reference) {
        // Verify payment with Paystack
        var verifyResponse = paystackService.verifyPayment(reference);

        if (verifyResponse == null || !verifyResponse.isStatus()) {
            throw new RuntimeException("Payment verification failed");
        }

        // Get payment from database
        Payment payment = paymentRepository.findByPaystackReference(reference)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // Update payment status
        if ("success".equalsIgnoreCase(verifyResponse.getData().getStatus())) {
            payment.setStatus(PaymentStatus.SUCCESS);

            // Auto-settle to zone account if enabled
            if (enableAutoSettlement) {
                try {
                    settlementService.transferToZone(payment);
                } catch (Exception e) {
                    // Log error but don't fail the payment verification
                    System.err.println("Failed to settle payment to zone: " + e.getMessage());
                }
            }
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByReference(String reference) {
        return paymentRepository.findByPaystackReference(reference)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public boolean verifyPayment(Map<String, Object> payload) {
        try {
            // Get payment reference from callback payload
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            String reference = (String) data.get("reference");

            // Verify payment with Paystack API
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.paystack.co/transaction/verify/" + reference;

            org.springframework.http.HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + paystackConfig.getSecretKey());
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && Boolean.TRUE.equals(responseBody.get("status"))) {
                Map<String, Object> paymentData = (Map<String, Object>) responseBody.get("data");
                String status = (String) paymentData.get("status");
                return "success".equalsIgnoreCase(status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}



