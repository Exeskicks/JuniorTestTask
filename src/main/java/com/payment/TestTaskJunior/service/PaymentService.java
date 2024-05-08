package com.payment.TestTaskJunior.service;

import com.payment.TestTaskJunior.controller.payload.request.PayPhoneDto;
import com.payment.TestTaskJunior.controller.payload.response.PayResultDto;
import com.payment.TestTaskJunior.controller.payload.response.PaymentDto;
import com.payment.TestTaskJunior.model.Payment;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

public interface PaymentService {

    PaymentDto currentBalanceByAuthorityUser(Principal principal);

    PayResultDto payPhone(PayPhoneDto payPhoneDto, Principal principal);

    PageImpl<Payment> getHistory(Pageable pageable, Principal principal);
}

