package com.medcon.auth.service;

import com.medcon.auth.dto.AuthResponse;
import com.medcon.auth.dto.LoginRequest;
import com.medcon.auth.dto.RegisterRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    Authentication getAuthentication();
}
