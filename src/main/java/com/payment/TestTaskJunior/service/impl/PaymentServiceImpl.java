package com.payment.TestTaskJunior.service.impl;


import com.payment.TestTaskJunior.controller.payload.request.PayPhoneDto;
import com.payment.TestTaskJunior.controller.payload.response.PayResultDto;
import com.payment.TestTaskJunior.controller.payload.response.PaymentDto;
import com.payment.TestTaskJunior.model.Payment;
import com.payment.TestTaskJunior.model.UserAccount;
import com.payment.TestTaskJunior.repository.PaymentRepository;
import com.payment.TestTaskJunior.repository.UserRepository;
import com.payment.TestTaskJunior.service.PaymentService;
import com.payment.TestTaskJunior.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;

    @Override
    public PaymentDto currentBalanceByAuthorityUser(Principal principal) {

        UserAccount user = getAuthorizationUser(principal);

        return new PaymentDto(user.getPhoneNumber(), user.getBalance());
    }

    private UserAccount getAuthorizationUser(Principal principal) {

        return userRepository.findUserByPhoneNumber(principal.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public PayResultDto payPhone(PayPhoneDto payPhoneDto, Principal principal) {
        String phoneNumber = payPhoneDto.phoneNumber();
        BigDecimal amount = payPhoneDto.amount();

        UserAccount paymentReceiver = userRepository.findUserByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));

        UserAccount paymentSender = getAuthorizationUser(principal);

        if (paymentSender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            return new PayResultDto("Недостаточно средств");
        }

        paymentSender.setBalance(paymentSender.getBalance().subtract(amount));
        userRepository.save(paymentSender);

        paymentReceiver.setBalance(paymentReceiver.getBalance().add(amount));

        userRepository.save(paymentReceiver);

        String message = "Оплата на номер %s прошла успешно, ваш текущий баланс %.2f";

        return new PayResultDto(
            message.formatted(phoneNumber, paymentSender.getBalance())
        );

    }

    @Override
    public PageImpl<Payment> getHistory(Pageable pageable, Principal principal) {
        UserAccount user = getAuthorizationUser(principal);
        return new PageImpl<>(user.getPaymentHistory());

    }

}