package com.medcon.auth.dto;

import com.medcon.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email @NotBlank String email,
        @Size(min = 6, message = "Password must be at least 6 characters long") String password,
        @NotNull Role role
) {
}
