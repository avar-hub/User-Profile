package com.socials.UserProfile.controller;

import com.socials.UserProfile.entity.SwipeRecord;
import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.service.UserProfileService;
import com.socials.UserProfile.service.UserProfileServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getPeopleNearByMe")
    public ResponseEntity<List<UserProfile>> showPeople(@RequestParam String email){
        return ResponseEntity.ok().body(userProfileService.showPeople(email));
    }

    @PostMapping("/swipeRight")
    public ResponseEntity<String> saveRightSwipes(@RequestBody SwipeRecord swipeRecord){
        return ResponseEntity.ok().body(userProfileService.saveRightSwipes(swipeRecord));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserProfile> updateUserProfile(@RequestBody @Valid UserProfile userProfile , @RequestParam String email){
        return ResponseEntity.ok().body(userProfileService.updateUserProfile(userProfile,email));
    }
}
