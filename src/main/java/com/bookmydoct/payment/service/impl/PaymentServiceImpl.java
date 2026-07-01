package com.bookmydoct.payment.service.impl;

import com.bookmydoct.appointment.data.entity.Appointment;
import com.bookmydoct.appointment.data.enums.AppointmentStatus;
import com.bookmydoct.appointment.repository.AppointmentRepository;
import com.bookmydoct.common.exception.customException.AppointmentNotFoundException;
import com.bookmydoct.common.exception.customException.InvalidPaymentStateException;
import com.bookmydoct.common.exception.customException.PaymentAlreadyExistsException;
import com.bookmydoct.common.exception.customException.PaymentNotFoundException;
import com.bookmydoct.payment.data.dto.request.InitiatePaymentRequest;
import com.bookmydoct.payment.data.dto.request.RazorpayPaymentVerificationRequest;
import com.bookmydoct.payment.data.dto.response.PaymentResponse;
import com.bookmydoct.payment.data.dto.response.RazorpayOrderResponse;
import com.bookmydoct.payment.data.entity.Payment;
import com.bookmydoct.payment.data.enums.PaymentStatus;
import com.bookmydoct.payment.util.PaymentMapper;
import com.bookmydoct.payment.repository.PaymentRepository;
import com.bookmydoct.payment.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key-id}")
    private String razorpayKeyId;

    @Value("${razorpay.key-secret}")
    private String razorpayKeySecret;

    @Override
    public RazorpayOrderResponse initiatePayment(InitiatePaymentRequest request) {

        Appointment appointment =
                appointmentRepository
                        .findByUuid(request.getAppointmentUuid())
                        .orElseThrow(() ->
                                new AppointmentNotFoundException("Appointment not found"));

        boolean alreadyPaid = paymentRepository
                        .existsByAppointment_IdAndStatus(
                                appointment.getId(), PaymentStatus.SUCCESS);

        if (alreadyPaid) {
            throw new PaymentAlreadyExistsException(
                    "This appointment has already been paid for");
        }

        BigDecimal amount = appointment.getDoctor().getConsultationFee();

        try {
            Order razorpayOrder = createRazorpayOrder(amount);

            Payment payment = new Payment();
            payment.setAppointment(appointment);
            payment.setAmount(amount);
            payment.setPaymentMethod(request.getPaymentMethod());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setRazorpayOrderId(razorpayOrder.get("id"));

            Payment saved = paymentRepository.save(payment);

            return RazorpayOrderResponse
                    .builder()
                    .paymentUuid(saved.getUuid().toString())
                    .razorpayOrderId(razorpayOrder.get("id"))
                    .keyId(razorpayKeyId)
                    .amount(amount)
                    .currency("INR")
                    .build();

        }
        catch (RazorpayException e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    @Override
    public PaymentResponse verifyPayment(RazorpayPaymentVerificationRequest request) {

        Payment payment = paymentRepository
                        .findByRazorpayOrderId(request.getRazorpayOrderId())
                        .orElseThrow(() ->
                                new PaymentNotFoundException(
                                        "Payment not found"));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new InvalidPaymentStateException("Payment already processed");
        }

        boolean verified = verifySignature(
                        request.getRazorpayOrderId(),
                        request.getRazorpayPaymentId(),
                        request.getRazorpaySignature());

        if (!verified) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

            throw new InvalidPaymentStateException(
                    "Razorpay signature verification failed");
        }
        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
        payment.setRazorpaySignature(request.getRazorpaySignature());
        payment.setStatus(PaymentStatus.SUCCESS);
        Appointment appointment = payment.getAppointment();
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new InvalidPaymentStateException(
                    "Appointment is not in payable state");
        }
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentRepository.save(appointment);
        Payment saved = paymentRepository.save(payment);

        return PaymentMapper.toResponse(saved);
    }

    private boolean verifySignature(
            String orderId,
            String paymentId,
            String signature) {

        try {
            JSONObject attributes = new JSONObject();
            attributes.put("razorpay_order_id", orderId);
            attributes.put("razorpay_payment_id", paymentId);
            attributes.put("razorpay_signature", signature);

            return Utils.verifyPaymentSignature(attributes, razorpayKeySecret);
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByUuid(UUID paymentUuid) {

        Payment payment = paymentRepository.findByUuid(paymentUuid)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        return PaymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByAppointmentUuid(UUID appointmentUuid) {

        List<Payment> payments =
                paymentRepository.findByAppointment_UuidOrderByCreatedAtDesc(appointmentUuid);

        return payments.stream().map(PaymentMapper::toResponse).toList();
    }

//    helper method to create razorpay order
private Order createRazorpayOrder(BigDecimal amount)
        throws RazorpayException {

    JSONObject options = new JSONObject();

    options.put("amount",
            amount.multiply(BigDecimal.valueOf(100)).intValue());
    options.put("currency", "INR");
    options.put("receipt", "rcpt_" + System.currentTimeMillis());

    return razorpayClient.orders.create(options);
}
}