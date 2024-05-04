package com.payment.TestTaskJunior.controller.response;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String phoneNumber;
    private BigDecimal balance;
}
