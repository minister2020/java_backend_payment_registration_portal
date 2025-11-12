package com.example.AdvancedUser.repository;


import com.example.AdvancedUser.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaystackReference(String paystackReference);
}




