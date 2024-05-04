package com.payment.TestTaskJunior.service.impl;

import com.payment.TestTaskJunior.model.UserAccount;
import com.payment.TestTaskJunior.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByPhoneNumber(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("UserAccount not found"));
    }


    public User map(UserAccount userAccount) {
        return new User(
                userAccount.getPhoneNumber(),
                userAccount.getPassword(),
                userAccount.getRole());
    }
}