package com.payment.TestTaskJunior.controller;


import com.payment.TestTaskJunior.controller.payload.request.PayPhoneDto;
import com.payment.TestTaskJunior.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/current-balance")
    public ResponseEntity<?> currentBalance(Principal principal) {
        return ResponseEntity
            .ok(paymentService.currentBalanceByAuthorityUser(principal));
    }

    @PostMapping("/pay-phone")
    public ResponseEntity<?> payPhone(@RequestBody PayPhoneDto payPhoneDto, Principal principal) {
        return ResponseEntity
            .ok(paymentService.payPhone(payPhoneDto, principal));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getHistory(Pageable pageable, Principal principal) {
        return ResponseEntity
            .ok(paymentService.getHistory(pageable, principal));
    }
}
