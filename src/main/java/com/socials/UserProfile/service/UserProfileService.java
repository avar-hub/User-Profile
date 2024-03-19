package com.socials.UserProfile.service;

import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.exception.UserNotSavedException;
import com.socials.UserProfile.repository.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepo repo;

    public String saveUserProfile(UserProfile userProfile){
        UserProfile userProfile1=repo.save(userProfile);
        if(userProfile1!=null)
            return "User Saved Successfully";
        else
            throw new UserNotSavedException("User Not Saved");
    }
}
