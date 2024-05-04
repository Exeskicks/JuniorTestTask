package com.payment.TestTaskJunior.service.impl;


import com.payment.TestTaskJunior.model.UserRole;
import com.payment.TestTaskJunior.repository.UserRoleRepository;
import com.payment.TestTaskJunior.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public Optional<UserRole> findByAuthority(String authority) {
        return userRoleRepository.findByAuthority(authority);
    }

    @Override
    public Optional<UserRole> findUserRole() {
        return findByAuthority("ROLE_USER");
    }
}
