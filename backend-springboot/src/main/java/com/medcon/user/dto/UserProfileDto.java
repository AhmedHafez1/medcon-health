package com.medcon.user.dto;

import com.medcon.user.entity.Role;

import java.time.LocalDate;

public record UserProfileDto(Long userId, LocalDate dob, String gender, String phone, String address,
                             String profilePicture, String fullName, String email, Role role) {
}
