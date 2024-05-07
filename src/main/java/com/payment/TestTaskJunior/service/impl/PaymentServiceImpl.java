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
import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentDto currentBalanceByAuthorityUser() {
        UserAccount user = getAuthorizationUser();

        return new PaymentDto(user.getPhoneNumber(), user.getBalance());
    }

    private UserAccount getAuthorizationUser() {
        String phoneNumber = ((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername();

        return userRepository.findUserByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public PayResultDto payPhone(PayPhoneDto payPhoneDto) {
        String phoneNumber = payPhoneDto.phoneNumber();
        BigDecimal amount = payPhoneDto.amount();
        return userRepository
            .findUserByPhoneNumber(phoneNumber)
            .map(usr -> {
                BigDecimal subtract = usr.getBalance().subtract((amount));
                if (subtract.compareTo(BigDecimal.ZERO) < 0) {
                    return new PayResultDto("Недостаточно средств");
                }
                usr.setBalance(subtract);
                Payment savedPayment = paymentRepository.save(getPayment(amount, phoneNumber));
                usr.getPaymentHistory().add(savedPayment);
                userRepository.save(usr);
                String message = "Оплата на номер %s прошла успешно, текущий баланс %.2f";
                return new PayResultDto(
                    message.formatted(phoneNumber, usr.getBalance().doubleValue()));
            })
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public PageImpl<Payment> getHistory(Pageable pageable) {
        UserAccount user = getAuthorizationUser();
        return new PageImpl<>(user.getPaymentHistory());

    }

    private Payment getPayment(BigDecimal amount, String phoneNumber) {
        Payment payment = new Payment();
        payment.setBalance(amount);
        payment.setPhoneNumber(phoneNumber);
        payment.setDate(LocalDateTime.now());
        return payment;
    }
}