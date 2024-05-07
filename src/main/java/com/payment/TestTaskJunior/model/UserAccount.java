package com.payment.TestTaskJunior.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user_entity")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender;
    LocalDate birthDate;
    String phoneNumber;
    String password;
    BigDecimal balance;

    @OneToMany(fetch = FetchType.EAGER)
    Set<UserRole> role = new HashSet<>();


    @OneToMany
    List<Payment> paymentHistory = new ArrayList<>();
}
