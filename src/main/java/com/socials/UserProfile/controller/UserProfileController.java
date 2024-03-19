package com.socials.UserProfile.controller;

import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody @Valid UserProfile userProfile) {
        return ResponseEntity.ok()
                .body(userProfileService.saveUserProfile(userProfile));
    }
}
