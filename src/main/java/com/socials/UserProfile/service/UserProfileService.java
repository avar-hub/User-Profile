package com.socials.UserProfile.service;

import com.socials.UserProfile.entity.SwipeRecord;
import com.socials.UserProfile.entity.UserProfile;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserProfileService {

    public String saveUserProfile(UserProfile userProfile, String email);

    UserProfile getUserProfileByEmail(String email);

    String deleteUserProfileByEmail(String email);

    List<UserProfile> showPeople(String email);

    String saveRightSwipes(SwipeRecord swipeRecord);
}
