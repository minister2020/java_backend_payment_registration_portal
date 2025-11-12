package com.example.AdvancedUser.controller;

import com.example.AdvancedUser.dto.PaymentRequest;
import com.example.AdvancedUser.dto.PaystackInitializeResponse;
import com.example.AdvancedUser.model.Payment;
import com.example.AdvancedUser.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initialize")
    public ResponseEntity<PaystackInitializeResponse> initializePayment(@Valid @RequestBody PaymentRequest request) {
        try {
            PaystackInitializeResponse response = paymentService.initializePayment(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/verify/{reference}")
    public ResponseEntity<?> verifyPayment(@PathVariable String reference) {
        try {
            Payment payment = paymentService.verifyAndUpdatePayment(reference);
            return ResponseEntity.ok(payment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage(), "status", "error"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "An error occurred while verifying payment", "status", "error"));
        }
    }

    @GetMapping("/{reference}")
    public ResponseEntity<Payment> getPayment(@PathVariable String reference) {
        try {
            Payment payment = paymentService.getPaymentByReference(reference);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


