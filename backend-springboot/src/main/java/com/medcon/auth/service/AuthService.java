package com.medcon.auth.service;

import com.medcon.auth.dto.AuthResponse;
import com.medcon.auth.dto.LoginRequest;
import com.medcon.auth.dto.RegisterRequest;
import com.medcon.user.entity.User;
import com.medcon.auth.mapper.AuthMapper;
import com.medcon.user.repository.UserRepository;
import com.medcon.exception.AlreadyExistsException;
import com.medcon.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    public AuthResponse register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.email()))
            throw new AlreadyExistsException("Email already exists!", "User");

        User user = authMapper.toUser(request);

        userRepo.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getFullName(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Email does not exists!"));

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new InvalidCredentialsException("Invalid password!");

        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getFullName(), user.getRole().name());
    }
}
