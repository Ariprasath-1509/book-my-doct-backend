package com.bookmydoct.payment.controller;

import com.bookmydoct.common.response.ApiResponse;
import com.bookmydoct.payment.data.dto.request.InitiatePaymentRequest;
import com.bookmydoct.payment.data.dto.request.RazorpayPaymentVerificationRequest;
import com.bookmydoct.payment.data.dto.response.PaymentResponse;
import com.bookmydoct.payment.data.dto.response.RazorpayOrderResponse;
import com.bookmydoct.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/payments/initiate")
    public ResponseEntity<ApiResponse<RazorpayOrderResponse>> initiatePayment(
            @Valid @RequestBody InitiatePaymentRequest request) {

        RazorpayOrderResponse response = paymentService.initiatePayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<RazorpayOrderResponse>builder()
                        .success(true)
                        .message("Payment initiated successfully")
                        .data(response)
                        .build());
    }

    @PostMapping("/api/payments/razorpay/verify")
    public ApiResponse<PaymentResponse> verifyPayment(
            @RequestBody RazorpayPaymentVerificationRequest request) {

        PaymentResponse response = paymentService.verifyPayment(request);

        return ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment verified successfully")
                .data(response)
                .build();
    }

    @GetMapping("/api/payments/{paymentUuid}")
    public ApiResponse<PaymentResponse> getPaymentByUuid(
            @PathVariable UUID paymentUuid) {

        PaymentResponse response = paymentService.getPaymentByUuid(paymentUuid);

        return ApiResponse.<PaymentResponse>builder()
                .success(true)
                .message("Payment fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/api/appointments/{appointmentUuid}/payments")
    public ApiResponse<List<PaymentResponse>> getPaymentsByAppointmentUuid(
            @PathVariable UUID appointmentUuid) {

        List<PaymentResponse> response =
                paymentService.getPaymentsByAppointmentUuid(appointmentUuid);

        return ApiResponse.<List<PaymentResponse>>builder()
                .success(true)
                .message("Payment history fetched successfully")
                .data(response)
                .build();
    }
}