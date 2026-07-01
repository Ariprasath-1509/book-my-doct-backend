package com.bookmydoct.payment.util;

import com.bookmydoct.payment.data.dto.response.PaymentResponse;
import com.bookmydoct.payment.data.entity.Payment;

public final class PaymentMapper {

    private PaymentMapper() {}

    public static PaymentResponse toResponse(Payment payment) {

        return PaymentResponse.builder()
                .uuid(payment.getUuid())
                .appointmentUuid(payment.getAppointment().getUuid())
                .appointmentReference(payment.getAppointment().getAppointmentReference())
                .amount(payment.getAmount())
                .razorpayOrderId(payment.getRazorpayOrderId())
                .razorpayPaymentId(payment.getRazorpayPaymentId())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}