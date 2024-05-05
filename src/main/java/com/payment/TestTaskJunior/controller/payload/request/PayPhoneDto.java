package com.payment.TestTaskJunior.controller.payload.request;

import java.math.BigDecimal;

public record PayPhoneDto(
    String phoneNumber,
    BigDecimal amount
) {

}

