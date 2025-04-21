package com.medcon.user.controller;

import com.medcon.user.dto.UserProfileDto;
import com.medcon.user.service.UserProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public UserProfileDto getUserProfile() {
        return userProfileService.getUserProfile();
    }

    @PostMapping
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto) {
        return userProfileService.updateUserProfile(userProfileDto);
    }
}
