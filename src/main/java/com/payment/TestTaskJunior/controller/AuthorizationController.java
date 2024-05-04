package com.payment.TestTaskJunior.controller;


import com.payment.TestTaskJunior.controller.response.RegistrationDto;
import com.payment.TestTaskJunior.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthorizationController {

    private final UserService userService;

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDto registrationDto) {
        return ResponseEntity
                .ok(userService.registration(registrationDto));
    }




}