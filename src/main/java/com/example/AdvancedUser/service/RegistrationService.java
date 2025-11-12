package com.example.AdvancedUser.service;

import com.example.AdvancedUser.dto.RegistrationRequest;
import com.example.AdvancedUser.dto.RegistrationResponse;
import com.example.AdvancedUser.model.Payment;
import com.example.AdvancedUser.model.PaymentStatus;
import com.example.AdvancedUser.model.Registrations;
import com.example.AdvancedUser.model.Zone;
import com.example.AdvancedUser.repository.PaymentRepository;
import com.example.AdvancedUser.repository.RegistrationRepository;
import com.example.AdvancedUser.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private EmailService emailService;

    public Registrations createRegistration(RegistrationRequest request) {
        // Verify payment is successful
        Payment payment = paymentRepository.findByPaystackReference(request.getPaymentReference())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Payment not confirmed");
        }

        // Check if all registrations are already submitted
        List<Registrations> existingRegistrations = registrationRepository.findByPaymentId(payment.getId());
        if (existingRegistrations.size() >= payment.getNumberOfRegistrants()) {
            throw new RuntimeException("All registrations have already been submitted");
        }

        // Get zone
        Zone zone = zoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zone not found"));

        // Create registration
        Registrations registration = new Registrations();
        registration.setFirstTimeAttendingCamp(registration.getFirstTimeAttendingCamp());
        registration.setChildName(request.getChildName());
        registration.setAge(request.getAge());
        registration.setLocation(request.getLocation());
        registration.setZone(zone);
        registration.setAddress(request.getAddress());
        registration.setParentName(request.getParentName());
        registration.setParentPhone(request.getParentPhone());
        registration.setAllergy(request.getAllergy());
        registration.setPayment(payment);

        registration = registrationRepository.save(registration);

        // Check if all registrations are complete, then send email
        List<Registrations> allRegistrations = registrationRepository.findByPaymentId(payment.getId());
        if (allRegistrations.size() >= payment.getNumberOfRegistrants()) {
            emailService.sendRegistrationConfirmationEmail(payment.getEmail(), allRegistrations);
        }

        return registration;
    }

    public List<Registrations> getRegistrationsByPayment(String paymentReference) {
        Payment payment = paymentRepository.findByPaystackReference(paymentReference)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return registrationRepository.findByPaymentId(payment.getId());
    }

    public List<RegistrationResponse> getAllRegistrations() {
        List<Registrations> registrations = registrationRepository.findAllOrderByCreatedAtDesc();
        return convertToResponseList(registrations);
    }

    public List<RegistrationResponse> getRegistrationsByZone(Long zoneId) {
        List<Registrations> registrations = registrationRepository.findByZoneId(zoneId);
        return convertToResponseList(registrations);
    }

    public List<RegistrationResponse> getRegistrationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<Registrations> registrations = registrationRepository.findByCreatedAtBetween(startDate, endDate);
        return convertToResponseList(registrations);
    }

    public List<RegistrationResponse> getRegistrationsByZoneAndDateRange(Long zoneId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Registrations> registrations = registrationRepository.findByZoneIdAndCreatedAtBetween(zoneId, startDate, endDate);
        return convertToResponseList(registrations);
    }

    private List<RegistrationResponse> convertToResponseList(List<Registrations> registrations) {
        return registrations.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private RegistrationResponse convertToResponse(Registrations registration) {
        RegistrationResponse response = new RegistrationResponse();
        response.setId(registration.getId());
        response.setFirstTimeAttendingCamp(response.getFirstTimeAttendingCamp());
        response.setChildName(registration.getChildName());
        response.setAge(registration.getAge());
        response.setLocation(registration.getLocation());
        response.setZoneName(registration.getZone().getName());
        response.setZoneId(registration.getZone().getId());
        response.setAddress(registration.getAddress());
        response.setParentName(registration.getParentName());
        response.setParentPhone(registration.getParentPhone());
        response.setAllergy(registration.getAllergy());
        response.setCreatedAt(registration.getCreatedAt());

        Payment payment = registration.getPayment();
        if (payment != null) {
            response.setPaymentEmail(payment.getEmail());
            response.setPaymentReference(payment.getPaystackReference());
            response.setPaymentStatus(payment.getStatus());
            response.setTotalAmount(payment.getTotalAmount());
            response.setPaymentCreatedAt(payment.getCreatedAt());
        }

        return response;
    }
}



