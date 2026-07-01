package com.bookmydoct.payment.data.dto.response;

import com.bookmydoct.payment.data.enums.PaymentMethod;
import com.bookmydoct.payment.data.enums.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private UUID uuid;
    private UUID appointmentUuid;
    private String appointmentReference;
    private BigDecimal amount;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}