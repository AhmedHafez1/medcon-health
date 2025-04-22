package com.medcon.user.service;

import com.medcon.user.dto.UserProfileDto;

/**
 * UserProfileService interface for managing user profiles.
 * This interface defines the contract for user profile operations.
 */

public interface UserProfileService {
    UserProfileDto getUserProfile(Long userId);

    UserProfileDto updateUserProfile(UserProfileDto userProfileDto);
}
