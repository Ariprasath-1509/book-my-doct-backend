package com.bookmydoct.payment.repository;

import com.bookmydoct.payment.data.entity.Payment;
import com.bookmydoct.payment.data.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUuid(UUID uuid);

    boolean existsByAppointment_IdAndStatus(Long appointmentId, PaymentStatus status);

    List<Payment> findByAppointment_UuidOrderByCreatedAtDesc(UUID appointmentUuid);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    List<Payment> findByStatusAndCreatedAtBefore(
            PaymentStatus status,
            LocalDateTime time);

    long countByStatus(PaymentStatus status);

    @Query("""
    SELECT COALESCE(SUM(p.amount),0)
    FROM Payment p
    WHERE p.status='SUCCESS'
    """)
    BigDecimal getTotalRevenue();

    @Query("""
    SELECT COALESCE(SUM(p.amount),0)
    FROM Payment p
    WHERE p.status='SUCCESS'
    AND DATE(p.createdAt)=CURRENT_DATE
    """)
    BigDecimal getTodayRevenue();
}