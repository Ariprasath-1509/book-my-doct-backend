package com.bookmydoct.payment.data.dto.request;

import com.bookmydoct.payment.data.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "Appointment UUID is required")
    private UUID appointmentUuid;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}