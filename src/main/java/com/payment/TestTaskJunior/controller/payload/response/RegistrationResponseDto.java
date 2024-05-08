package com.payment.TestTaskJunior.controller.payload.response;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponseDto {

    private Long userId;
    private String phoneNumber;
    private BigDecimal balance;
    private String token;
}
