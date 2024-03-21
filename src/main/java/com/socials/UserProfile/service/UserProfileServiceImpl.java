package com.socials.UserProfile.service;

import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.exception.UserNotFoundException;
import com.socials.UserProfile.exception.UserNotSavedException;
import com.socials.UserProfile.repository.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepo repo;

    @Override
    public String saveUserProfile(UserProfile userProfile, String email){
        userProfile.setEmail(email);
        UserProfile userProfile1=repo.save(userProfile);
        if(userProfile1!=null)
            return "User Saved Successfully";
        else
            throw new UserNotSavedException("User Not Saved");
    }

    @Override
    public UserProfile getUserProfileByEmail(String email) {
       UserProfile userProfile= repo.findByEmail(email)
               .orElseThrow(()-> new UserNotFoundException("User not found"));
           return userProfile;
    }

    @Override
    public String deleteUserProfileByEmail(String email) {
        UserProfile userProfile= repo.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        repo.delete(userProfile);
        return "User deleted successfully";
    }
}
