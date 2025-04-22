package com.medcon.user.controller;

import com.medcon.user.dto.UserProfileDto;
import com.medcon.user.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public UserProfileDto getUserProfile(@RequestParam Long userId) {
        return userProfileService.getUserProfile(userId);
    }

    @PostMapping
    public UserProfileDto updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        return userProfileService.updateUserProfile(userProfileDto);
    }
}
