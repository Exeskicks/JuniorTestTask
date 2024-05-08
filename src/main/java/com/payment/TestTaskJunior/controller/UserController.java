package com.payment.TestTaskJunior.controller;


import com.payment.TestTaskJunior.controller.payload.response.UserDto;
import com.payment.TestTaskJunior.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getMyAccount(){
        return ResponseEntity.ok(userService.getMyAccount());
    }

    @PostMapping
    public ResponseEntity<UserDto> updateMyAccount(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateMyAccount(userDto));
    }
}
