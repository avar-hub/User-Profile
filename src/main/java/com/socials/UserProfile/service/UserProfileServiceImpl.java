package com.socials.UserProfile.service;

import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.exception.UserNotFoundException;
import com.socials.UserProfile.exception.UserNotSavedException;
import com.socials.UserProfile.repository.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepo repo;

    @Override
    public String saveUserProfile(UserProfile userProfile, String email) {
        userProfile.setEmail(email);
        UserProfile userProfile1 = repo.save(userProfile);
        if (userProfile1 != null)
            return "User Saved Successfully";
        else
            throw new UserNotSavedException("User Not Saved");
    }

    @Override
    public UserProfile getUserProfileByEmail(String email) {
        UserProfile userProfile = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userProfile;
    }

    @Override
    public String deleteUserProfileByEmail(String email) {
        UserProfile userProfile = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        repo.delete(userProfile);
        return "User deleted successfully";
    }

    @Override
    public List<UserProfile> showPeople(String email) {
        UserProfile user = repo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<UserProfile> userProfiles = repo.findAllByCity(user.getCity());

        return userProfiles.stream()
                .filter(userProfile -> !userProfile.getEmail().equals(email))
                .filter(userProfile -> {
                    if (user.getSexualOrientation().equals("Bisexual")) {
                        return true;
                    }else if (user.getGender().equals("Male")) {
                        return userProfile.getGender().equals("Male") &&
                                (user.getSexualOrientation().equals("Gay") && (userProfile.getSexualOrientation().equals("Gay") || userProfile.getSexualOrientation().equals("Bisexual"))) ||
                                (user.getSexualOrientation().equals("Straight") && (userProfile.getGender().equals("Female") && (userProfile.getSexualOrientation().equals("Straight") || userProfile.getSexualOrientation().equals("Bisexual"))));
                    } else  {
                        return userProfile.getGender().equals("Female") &&
                                (user.getSexualOrientation().equals("Lesbian") && (userProfile.getSexualOrientation().equals("Lesbian") || userProfile.getSexualOrientation().equals("Bisexual"))) ||
                                (user.getSexualOrientation().equals("Straight") && (userProfile.getGender().equals("Male") && (userProfile.getSexualOrientation().equals("Straight") || userProfile.getSexualOrientation().equals("Bisexual"))));
                    }
                })
                .collect(Collectors.toList());

    }
}
