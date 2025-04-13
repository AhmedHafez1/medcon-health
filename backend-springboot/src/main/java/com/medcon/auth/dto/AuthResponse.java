package com.medcon.auth.dto;

public record AuthResponse(
        String token,
        String fullName,
        String role
) {
}
