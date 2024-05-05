package com.payment.TestTaskJunior.service;

import com.payment.TestTaskJunior.model.UserRole;

import java.util.Optional;

public interface UserRoleService {

    Optional<UserRole> findByAuthority(String authority);

    Optional<UserRole> findUserRole();
}
