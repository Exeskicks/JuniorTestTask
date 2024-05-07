package com.payment.TestTaskJunior.controller.payload.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
public record UserDto (
    String firstName,
    String lastName,
    String email,
    String gender,
    LocalDate birthday
){


}
