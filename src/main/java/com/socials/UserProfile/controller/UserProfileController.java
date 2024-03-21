package com.socials.UserProfile.controller;

import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.service.UserProfileService;
import com.socials.UserProfile.service.UserProfileServiceImpl;
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
    public ResponseEntity<String> saveUser(@RequestBody @Valid UserProfile userProfile , @RequestParam String email) {
        return ResponseEntity.ok()
                .body(userProfileService.saveUserProfile(userProfile,email));
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<UserProfile> getByEmail(@RequestParam String email){
        return ResponseEntity.ok().body(userProfileService.getUserProfileByEmail(email));
    }

    @DeleteMapping("/deleteByEmail")
    public ResponseEntity<String> deleteByEmail(@RequestParam String email){
        return ResponseEntity.ok().body(userProfileService.deleteUserProfileByEmail(email));
    }
}
