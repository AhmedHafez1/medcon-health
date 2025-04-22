package com.medcon.user.dto;

import com.medcon.user.entity.Role;

public record UserDto(
        Long id,
        String email,
        Role role
) {
}