package com.medcon.auth.service;

import com.medcon.user.entity.User;

public interface JwtService {
    String extractUsername(String token);
    boolean isTokenValid(String token, User user);
    String generateToken(User user);
}
