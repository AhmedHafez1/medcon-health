package com.medcon.auth.dto;

import com.medcon.user.dto.UserDto;

public record AuthResponse(
        String token,
        UserDto user
) {
}
