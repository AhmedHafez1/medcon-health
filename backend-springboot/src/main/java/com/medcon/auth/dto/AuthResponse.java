package com.medcon.auth.dto;

public record AuthResponse(
        String token,
        String email,
        String role
) {
}
