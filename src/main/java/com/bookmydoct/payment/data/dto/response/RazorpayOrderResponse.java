package com.bookmydoct.payment.data.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RazorpayOrderResponse {

    private String paymentUuid;

    private String razorpayOrderId;

    private String keyId;

    private BigDecimal amount;

    private String currency;
}