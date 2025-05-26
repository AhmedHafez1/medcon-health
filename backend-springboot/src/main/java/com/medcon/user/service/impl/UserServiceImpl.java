package com.medcon.user.service.impl;

import com.medcon.auth.service.AuthService;
import com.medcon.user.dto.UserDto;
import com.medcon.exception.UnAuthorizedException;
import com.medcon.user.mapper.UserMapper;
import com.medcon.user.repository.UserRepository;
import com.medcon.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;

    /**
     * Retrieves the current authenticated user.
     *
     * @return UserDto object representing the current user.
     * @throws UnAuthorizedException if the user is not authenticated or not found.
     */
    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = authService.getAuthentication();
        String email = authentication.getName();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnAuthorizedException("User not found!"));

        return userMapper.toDto(user);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return List of UserDto objects representing all users.
     */
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }
}
