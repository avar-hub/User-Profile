package com.socials.UserProfile.service;

import com.socials.UserProfile.entity.UserProfile;

public interface UserProfileService {

    public String saveUserProfile(UserProfile userProfile, String email);

    UserProfile getUserProfileByEmail(String email);

    String deleteUserProfileByEmail(String email);
}
