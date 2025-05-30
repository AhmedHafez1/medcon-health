package com.medcon.user.dto;

import com.medcon.user.entity.Role;

import java.time.LocalDate;

public record UserProfileDto(
        Long id,
        Long userId,
        String firstName,
        String lastName,
        LocalDate dob,
        String gender,
        String phone,
        String address,
        String profilePicture,
        String email,
        Role role) {
}
