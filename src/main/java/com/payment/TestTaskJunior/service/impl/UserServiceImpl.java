package com.payment.TestTaskJunior.service.impl;

import com.payment.TestTaskJunior.controller.payload.request.RegistrationDto;
import com.payment.TestTaskJunior.controller.payload.response.RegistrationResponseDto;
import com.payment.TestTaskJunior.controller.payload.response.UserDto;
import com.payment.TestTaskJunior.model.Gender;
import com.payment.TestTaskJunior.model.UserAccount;
import com.payment.TestTaskJunior.model.UserRole;
import com.payment.TestTaskJunior.repository.UserRepository;
import com.payment.TestTaskJunior.service.UserRoleService;
import com.payment.TestTaskJunior.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passEncoder;

    @Override
    public RegistrationResponseDto registration(RegistrationDto registrationDto) {
        UserRole userRole = userRoleService.findUserRole()
            .orElseThrow(() -> new RuntimeException("User role not found"));

        UserAccount user = UserAccount.builder()
            .balance(BigDecimal.valueOf(1000))
            .username(registrationDto.userName())
            .phoneNumber(registrationDto.phoneNumber())
            .password(passEncoder.encode(registrationDto.password()))
            .role(Set.of(userRole))
            .build();

        UserAccount savedUser = userRepository.save(user);

        String token = getAuthorizationToken(registrationDto);

        return RegistrationResponseDto.builder()
            .userId(savedUser.getId())
            .balance(savedUser.getBalance())
            .phoneNumber(savedUser.getPhoneNumber())
            .token(token)
            .build();
    }

    private String getAuthorizationToken(RegistrationDto registrationDto) {
        byte[] loginPassword = "%s:%s".formatted(registrationDto.phoneNumber(),
            registrationDto.password()).getBytes();
        return "Basic %s".formatted(Base64.getEncoder().encodeToString(loginPassword));
    }

    @Override
    public Optional<UserAccount> findByPhoneNumber(String phoneNumber) {
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDto getMyAccount() {
        return findUserAccountByAuthorize()
            .map(this::mapUserAccountToUserDto)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }


    private Optional<UserAccount> findUserAccountByAuthorize() {
        String phoneNumber = ((User) SecurityContextHolder.getContext().getAuthentication()
            .getPrincipal()).getUsername();
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDto updateMyAccount(UserDto userDto) {
        UserAccount userAccount = findUserAccountByAuthorize()
            .map(usr -> updateUserAccount(userDto, usr))
            .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.save(userAccount);
        return mapUserAccountToUserDto(userAccount);
    }

    private UserAccount updateUserAccount(UserDto userDto, UserAccount user) {
        user.setEmail(userDto.email());
        user.setGender(Gender.valueOf(userDto.gender()));
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setBirthDate(userDto.birthday());
        return user;
    }


    private UserDto mapUserAccountToUserDto(UserAccount usr) {
        return UserDto.builder()
            .email(usr.getEmail())
            .birthday(usr.getBirthDate())
            .gender(usr.getGender() != null ? usr.getGender().name() : null)
            .lastName(usr.getLastName())
            .firstName(usr.getFirstName())
            .build();
    }
}