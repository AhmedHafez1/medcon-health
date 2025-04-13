package com.medcon.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
