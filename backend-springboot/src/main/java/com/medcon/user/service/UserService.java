package com.medcon.user.service;

import com.medcon.auth.dto.UserDto;
import com.medcon.exception.UnAuthorizedException;
import com.medcon.user.mapper.UserMapper;
import com.medcon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
            throw new UnAuthorizedException("User not authenticated!");


        String email = authentication.getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnAuthorizedException("User not found!"));

        return userMapper.toDto(user);
    }
}
