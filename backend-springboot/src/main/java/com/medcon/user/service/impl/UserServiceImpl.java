package com.medcon.user.service.impl;

import com.medcon.user.dto.UserDto;
import com.medcon.exception.UnAuthorizedException;
import com.medcon.user.mapper.UserMapper;
import com.medcon.user.repository.UserRepository;
import com.medcon.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserServiceImpl class for managing user-related operations.
 * This class implements the UserService interface and provides methods to retrieve user information.
 */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated())
            throw new UnAuthorizedException("User not authenticated!");


        String email = authentication.getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnAuthorizedException("User not found!"));

        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }
}
