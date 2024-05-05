package com.payment.TestTaskJunior.controller;


import com.payment.TestTaskJunior.controller.payload.request.PayPhoneDto;
import com.payment.TestTaskJunior.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/current-balance")
    public ResponseEntity<?> currentBalance(){
        return ResponseEntity.ok(paymentService.currentBalanceByAuthorityUser());
    }

    @PostMapping("/pay-phone")
    public ResponseEntity<?> payPhone(@RequestBody PayPhoneDto payPhoneDto){
        return ResponseEntity.ok(paymentService.payPhone(payPhoneDto));
    }
}
