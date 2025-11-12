package com.example.AdvancedUser.repository;

import com.example.AdvancedUser.model.Registrations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registrations, Long> {
    List<Registrations> findByPaymentId(Long paymentId);

    List<Registrations> findByZoneId(Long zoneId);

    @Query("SELECT r FROM Registrations r WHERE r.createdAt BETWEEN :startDate AND :endDate")
    List<Registrations> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Registrations r WHERE r.zone.id = :zoneId AND r.createdAt BETWEEN :startDate AND :endDate")
    List<Registrations> findByZoneIdAndCreatedAtBetween(@Param("zoneId") Long zoneId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT r FROM Registrations r ORDER BY r.createdAt DESC")
    List<Registrations> findAllOrderByCreatedAtDesc();
}




