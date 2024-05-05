package com.payment.TestTaskJunior.service.impl;


import com.payment.TestTaskJunior.controller.payload.request.PayPhoneDto;
import com.payment.TestTaskJunior.controller.payload.response.PayResultDto;
import com.payment.TestTaskJunior.controller.payload.response.PaymentDto;
import com.payment.TestTaskJunior.model.UserAccount;
import com.payment.TestTaskJunior.repository.PaymentRepository;
import com.payment.TestTaskJunior.repository.UserRepository;
import com.payment.TestTaskJunior.service.PaymentService;
import com.payment.TestTaskJunior.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public PaymentDto currentBalanceByAuthorityUser() {
        String phoneNumber = ((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername();

        UserAccount userNotFound = userRepository.findUserByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return new PaymentDto(userNotFound.getPhoneNumber(), userNotFound.getBalance());
    }

    @Override
    public PayResultDto payPhone(PayPhoneDto payPhoneDto) {
        String phoneNumber = payPhoneDto.phoneNumber();
        BigDecimal amount = payPhoneDto.amount();
        return userRepository
            .findUserByPhoneNumber(phoneNumber)
            .map(usr -> {
                if (usr.getBalance().compareTo((amount)) < 0) {
                    return new PayResultDto("Недостаточно средств");
                }
                usr.setBalance(usr.getBalance().subtract(amount));
                userRepository.save(usr);
                String message = "Оплата на номер %s прошла успешно, текущий баланс %f";
                return new PayResultDto(message.formatted(phoneNumber, usr.getBalance().doubleValue()));
            }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}