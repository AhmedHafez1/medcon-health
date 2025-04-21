package com.medcon.user.service;

import com.medcon.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserDto getCurrentUser();
    List<UserDto> getAllUsers();
}
