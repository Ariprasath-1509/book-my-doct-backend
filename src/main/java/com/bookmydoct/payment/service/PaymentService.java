package com.bookmydoct.payment.service;

import com.bookmydoct.payment.data.dto.request.InitiatePaymentRequest;
import com.bookmydoct.payment.data.dto.request.RazorpayPaymentVerificationRequest;
import com.bookmydoct.payment.data.dto.response.PaymentResponse;
import com.bookmydoct.payment.data.dto.response.RazorpayOrderResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    RazorpayOrderResponse initiatePayment(InitiatePaymentRequest request);

    PaymentResponse getPaymentByUuid(UUID paymentUuid);

    List<PaymentResponse> getPaymentsByAppointmentUuid(UUID appointmentUuid);

    PaymentResponse verifyPayment(RazorpayPaymentVerificationRequest request);
}