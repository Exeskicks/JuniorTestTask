package com.payment.TestTaskJunior.controller.payload.request;

public record RegistrationDto(
        String userName,
        String phoneNumber,
        String password
) {

}
