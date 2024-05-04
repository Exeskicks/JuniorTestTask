package com.payment.TestTaskJunior.service;

import com.payment.TestTaskJunior.controller.response.RegistrationDto;
import com.payment.TestTaskJunior.controller.response.UserDto;
import com.payment.TestTaskJunior.model.UserAccount;

import java.util.Optional;

public interface UserService {

    UserDto registration(RegistrationDto registrationDto);

    Optional<UserAccount>  findByPhoneNumber(String phoneNumber);

}
