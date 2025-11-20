package com.example.AdvancedUser.service;

import com.example.AdvancedUser.model.Registrations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void sendRegistrationConfirmationEmail(String email, List<Registrations> registrations) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Registration Confirmation - Payment Portal");

            StringBuilder body = new StringBuilder();
            body.append("Dear Parent/Guardian,\n\n");
            body.append("Your registration has been successfully completed!\n\n");
            body.append("Registration Details:\n");
            body.append("========================\n\n");

            for (int i = 0; i < registrations.size(); i++) {
                Registrations reg = registrations.get(i);
                body.append("Registrant ").append(i + 1).append(":\n");
                body.append("Child Name: ").append(reg.getChildName()).append("\n");
                body.append("Age: ").append(reg.getAge()).append("\n");
                body.append("Location: ").append(reg.getTcCenter()).append("\n");
                body.append("Zone: ").append(reg.getZone().getName()).append("\n");
                body.append("Address: ").append(reg.getAddress()).append("\n");
                body.append("Parent Name: ").append(reg.getParentName()).append("\n");
                body.append("Parent Phone: ").append(reg.getParentPhone()).append("\n");
                body.append("Allergy: ").append(reg.getAllergy()).append("\n\n");
            }

            body.append("Thank you for your registration!\n\n");
            body.append("Best regards,\n");
            body.append("Payment Portal Team");

            message.setText(body.toString());
            mailSender.send(message);
        } catch (Exception e) {
            // Log error but don't throw exception to prevent registration failure
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}


