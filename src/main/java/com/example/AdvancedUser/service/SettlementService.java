package com.example.AdvancedUser.service;

import com.example.AdvancedUser.config.PaystackConfig;
import com.example.AdvancedUser.model.Payment;
import com.example.AdvancedUser.model.Zone;
import com.example.AdvancedUser.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for handling zone-based payment settlement.
 *
 * This service implements Paystack Transfer API to transfer payments to zone-specific accounts.
 *
 * Note: For production use, you should:
 * 1. Create transfer recipients for each zone account
 * 2. Store recipient codes in the database
 * 3. Use recipient codes for transfers instead of account details each time
 */
@Service
public class SettlementService {

    @Autowired
    private PaystackConfig paystackConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZoneRepository zoneRepository;

    /**
     * Create a transfer recipient for a zone account.
     * This should be done once per zone and the recipient code should be stored.
     */
    public String createTransferRecipient(Zone zone) {
        String url = paystackConfig.getBaseUrl() + "/transferrecipient";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(paystackConfig.getSecretKey());

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "nuban");
        payload.put("name", zone.getName() + " Account");
        payload.put("account_number", zone.getAccountNumber());
        payload.put("bank_code", zone.getBankCode());
        payload.put("currency", "NGN");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            if (response.getBody() != null && (Boolean) response.getBody().get("status")) {
                Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
                return (String) data.get("recipient_code");
            }
        } catch (Exception e) {
            System.err.println("Error creating transfer recipient: " + e.getMessage());
        }
        return null;
    }

    /**
     * Transfer payment amount to zone account after successful payment.
     *
     * Uses stored recipient code if available, otherwise creates a new one.
     */
    public boolean transferToZone(Payment payment) {
        Zone zone = payment.getZone();

        // Amount in kobo (Paystack uses kobo)
        Integer amountInKobo = (int) (payment.getTotalAmount() * 100);

        // Use stored recipient code if available, otherwise create a new one
        String recipientCode = zone.getPaystackRecipientCode();
        if (recipientCode == null || recipientCode.isEmpty()) {
            recipientCode = createTransferRecipient(zone);
            if (recipientCode != null) {
                // Store recipient code in zone for future use
                zone.setPaystackRecipientCode(recipientCode);
                zoneRepository.save(zone);
            }
        }

        if (recipientCode == null || recipientCode.isEmpty()) {
            System.err.println("Failed to create/get transfer recipient for zone: " + zone.getName());
            return false;
        }

        String url = paystackConfig.getBaseUrl() + "/transfer";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(paystackConfig.getSecretKey());

        Map<String, Object> payload = new HashMap<>();
        payload.put("source", "balance");
        payload.put("amount", amountInKobo);
        payload.put("recipient", recipientCode);
        payload.put("reason", "Payment settlement for " + zone.getName());
        payload.put("reference", "SETTLE_" + payment.getPaystackReference());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            if (response.getBody() != null && (Boolean) response.getBody().get("status")) {
                System.out.println("Transfer successful to zone: " + zone.getName());
                return true;
            } else {
                System.err.println("Transfer failed: " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error transferring to zone: " + e.getMessage());
            return false;
        }
    }
}


