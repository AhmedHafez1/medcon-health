package com.medcon.auth.dto;

import com.medcon.auth.entity.Role;

public record RegisterRequest(
        String fullName,
        String email,
        String password,
        Role role
) {
}
