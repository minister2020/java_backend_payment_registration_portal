package com.example.AdvancedUser.controller;

import com.example.AdvancedUser.dto.RegistrationRequest;
import com.example.AdvancedUser.model.Registrations;
import com.example.AdvancedUser.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin(origins = "http://localhost:3000")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Registrations> createRegistration(@Valid @RequestBody RegistrationRequest request) {
        try {
            Registrations registration = registrationService.createRegistration(request);
            return ResponseEntity.ok(registration);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/payment/{paymentReference}")
    public ResponseEntity<List<Registrations>> getRegistrationsByPayment(@PathVariable String paymentReference) {
        try {
            List<Registrations> registrations = registrationService.getRegistrationsByPayment(paymentReference);
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


