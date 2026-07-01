package com.bookmydoct.payment.data.entity;

import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.common.entity.BaseEntity;
import com.bookmydoct.payment.data.enums.PaymentMethod;
import com.bookmydoct.payment.data.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="payments")
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

//    /**
//     * Gateway reference.
//     */
//    private String transactionId;

    @Column(unique = true)
    private String razorpayOrderId;

    private String razorpayPaymentId;

    @Column(length = 1000)
    private String razorpaySignature;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
