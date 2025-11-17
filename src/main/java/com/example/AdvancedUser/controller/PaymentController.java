package com.example.AdvancedUser.controller;

import com.example.AdvancedUser.dto.PaymentRequest;
import com.example.AdvancedUser.dto.PaystackInitializeResponse;
import com.example.AdvancedUser.model.Payment;
import com.example.AdvancedUser.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
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
//    @PostMapping("/payment/callback")
//    public ResponseEntity<String> handlePaystackCallback(@RequestBody Map<String, Object> payload) {
//        System.out.println("PAYSTACK CALLBACK ===> " + payload);
//        // verify payment & update DB
//        return ResponseEntity.ok("Callback received");
//    }

//    @PostMapping("/payment/callback")
//    public void handlePaystackCallback(@RequestBody Map<String, Object> payload,
//                                       HttpServletResponse response) throws IOException {
//        System.out.println("PAYSTACK CALLBACK ===> " + payload);
//
//        // Verify payment using your service (call Paystack API if needed)
//        boolean verified = paymentService.verifyPayment(payload);
//
//        // Update DB or mark user as paid if verified
//        if (verified) {
//            System.out.println("Payment verified successfully!");
//        } else {
//            System.out.println("Payment verification failed!");
//        }
//
//        // Redirect user to frontend success page
//        response.sendRedirect("https://691b73a5debe6b0008a65723--payment-portal-frontend.netlify.app/payment/callback");
//    }
@GetMapping("/payment/callback")
public ResponseEntity<Void> handlePaystackRedirect(@RequestParam Map<String, String> params) {
    System.out.println("PAYSTACK REDIRECT CALLBACK ===> " + params);

    // Optionally, verify payment on backend via params['reference']
    // Redirect to frontend registration or success page
    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("https://691b73a5debe6b0008a65723--payment-portal-frontend.netlify.app/registration")) // frontend registration URL
            .build();
}
}


