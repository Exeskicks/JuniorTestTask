package com.payment.TestTaskJunior.service.impl;

import com.payment.TestTaskJunior.controller.payload.request.RegistrationDto;
import com.payment.TestTaskJunior.controller.payload.response.UserDto;
import com.payment.TestTaskJunior.model.UserAccount;
import com.payment.TestTaskJunior.model.UserRole;
import com.payment.TestTaskJunior.repository.UserRepository;
import com.payment.TestTaskJunior.service.UserRoleService;
import com.payment.TestTaskJunior.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passEncoder;

    @Override
    public UserDto registration(RegistrationDto registrationDto) {
        UserRole userRole = userRoleService.findUserRole()
                .orElseThrow(() -> new RuntimeException("User role not found"));

        UserAccount user = UserAccount.builder()
                .balance(BigDecimal.valueOf(1000))
                .phoneNumber(registrationDto.phoneNumber())
                .password(passEncoder.encode(registrationDto.password()))
                .role(Set.of(userRole))
                .build();

        UserAccount savedUser = userRepository.save(user);
        return UserDto.builder()
                .userId(savedUser.getId())
                .balance(savedUser.getBalance())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    @Override
    public Optional<UserAccount> findByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }


}