package com.example.AdvancedUser.controller;


import com.example.AdvancedUser.dto.RegistrationResponse;
import com.example.AdvancedUser.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/registrations")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<RegistrationResponse>> getAllRegistrations(
            @RequestParam(required = false) Long zoneId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        try {
            List<RegistrationResponse> registrations;

            if (zoneId != null && startDate != null && endDate != null) {
                registrations = registrationService.getRegistrationsByZoneAndDateRange(zoneId, startDate, endDate);
            } else if (zoneId != null) {
                registrations = registrationService.getRegistrationsByZone(zoneId);
            } else if (startDate != null && endDate != null) {
                registrations = registrationService.getRegistrationsByDateRange(startDate, endDate);
            } else {
                registrations = registrationService.getAllRegistrations();
            }

            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getRegistrationStats() {
        try {
            List<RegistrationResponse> allRegistrations = registrationService.getAllRegistrations();

            long totalRegistrations = allRegistrations.size();
            long uniquePayments = allRegistrations.stream()
                    .map(RegistrationResponse::getPaymentReference)
                    .distinct()
                    .count();

            // Calculate total revenue by summing unique payment amounts (one amount per payment reference)
            double totalRevenue = allRegistrations.stream()
                    .collect(Collectors.toMap(
                            RegistrationResponse::getPaymentReference,
                            reg -> reg.getTotalAmount() != null ? reg.getTotalAmount() : 0.0,
                            (existing, replacement) -> existing // Keep first value if duplicate keys
                    ))
                    .values()
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return ResponseEntity.ok(new RegistrationStats(totalRegistrations, uniquePayments, totalRevenue));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Inner class for statistics
    public static class RegistrationStats {
        private long totalRegistrations;
        private long totalPayments;
        private double totalRevenue;

        public RegistrationStats(long totalRegistrations, long totalPayments, double totalRevenue) {
            this.totalRegistrations = totalRegistrations;
            this.totalPayments = totalPayments;
            this.totalRevenue = totalRevenue;
        }

        public long getTotalRegistrations() {
            return totalRegistrations;
        }

        public void setTotalRegistrations(long totalRegistrations) {
            this.totalRegistrations = totalRegistrations;
        }

        public long getTotalPayments() {
            return totalPayments;
        }

        public void setTotalPayments(long totalPayments) {
            this.totalPayments = totalPayments;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        public void setTotalRevenue(double totalRevenue) {
            this.totalRevenue = totalRevenue;
        }
    }
}


