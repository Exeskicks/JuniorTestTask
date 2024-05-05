package com.payment.TestTaskJunior.service;

import com.payment.TestTaskJunior.controller.payload.request.PayPhoneDto;
import com.payment.TestTaskJunior.controller.payload.response.PayResultDto;
import com.payment.TestTaskJunior.controller.payload.response.PaymentDto;

public interface PaymentService {

    PaymentDto currentBalanceByAuthorityUser();

    PayResultDto payPhone(PayPhoneDto payPhoneDto);
}

