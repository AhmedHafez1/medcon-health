package com.medcon.auth.dto;

import com.medcon.auth.entity.Role;

public record UserDto(
        Long id,
        String fullName,
        String email,
        Role role
) {
}