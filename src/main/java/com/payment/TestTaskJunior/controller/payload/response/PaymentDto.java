package com.payment.TestTaskJunior.controller.payload.response;

import java.math.BigDecimal;

public record PaymentDto(
        String phoneNumber,
        BigDecimal amount
) {
}
