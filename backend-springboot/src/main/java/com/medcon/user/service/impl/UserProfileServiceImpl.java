package com.medcon.user.service.impl;

import com.medcon.auth.service.AuthService;
import com.medcon.exception.ForbiddenException;
import com.medcon.exception.NotFoundException;
import com.medcon.user.dto.UserProfileDto;
import com.medcon.user.mapper.UserProfileMapper;
import com.medcon.user.repository.UserProfileRepository;
import com.medcon.user.repository.UserRepository;
import com.medcon.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final UserProfileMapper userProfileMapper;
    private final AuthService authService;

    @Override
    public UserProfileDto getUserProfile(Long userId) {
        var userProfile = userProfileRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User profile not found", "UserProfile"));

        return userProfileMapper.toDto(userProfile);
    }

    @Override
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto) {
        validateUserPermission(userProfileDto.email());

        var user = userRepository.findByEmail(userProfileDto.email());

        if (user.isEmpty()) {
            throw new NotFoundException("User not found", "User");
        }

        UserProfileDto profileDto;
        if (user.get().getUserProfile() == null) {
            profileDto = userProfileDto;
        } else {
            profileDto = userProfileMapper
                    .mergeProfile(user.get().getUserProfile(), userProfileDto);
        }

        var profile = userProfileMapper.toEntity(profileDto);
        profile.setUser(user.get());

        var savedProfile = userProfileRepository.save(profile);

        return userProfileMapper.toDto(savedProfile);
    }

    private void validateUserPermission(String requestedEmail) {
        var currentUserEmail = authService.getAuthentication().getName();
        if (!currentUserEmail.equals(requestedEmail)) {
            throw new ForbiddenException("You do not have permission to update this profile");
        }
    }
}
