package com.socials.UserProfile.service;

import com.socials.UserProfile.entity.MatchRecord;
import com.socials.UserProfile.entity.SwipeRecord;
import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.exception.UserNotFoundException;
import com.socials.UserProfile.exception.UserNotSavedException;
import com.socials.UserProfile.repository.MatchRecordRepo;
import com.socials.UserProfile.repository.SwipeRecordRepo;
import com.socials.UserProfile.repository.UserProfileRepo;
import com.socials.UserProfile.util.ImageServiceProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepo repo;

    private final SwipeRecordRepo swipeRecordRepo;

    private final MatchRecordRepo matchRecordRepo;

    private final ImageServiceProxy imageServiceProxy;

    @Override
    public UserProfile saveUserProfile(UserProfile userProfile, String email) {
        userProfile.setEmail(email);

//        Have to solve this error !!
//        userProfile.setImagePath(imageServiceProxy.getImagePath(email));
        return  repo.save(userProfile);
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

    @Cacheable(cacheNames = "cache1", key = "'#key1'")
    @Override
    public List<UserProfile> showPeople(String email) {
        UserProfile user = repo.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<UserProfile> userProfiles = repo.findAllByCity(user.getCity());

        return userProfiles.stream()
                .filter(userProfile -> !userProfile.getEmail().equals(email))
                .filter(userProfile -> {
                     if (user.getGender().equals("Male")) {
                        return userProfile.getGender().equals("Male") &&
                                (user.getSexualOrientation().equals("Gay") && (userProfile.getSexualOrientation().equals("Gay") || userProfile.getSexualOrientation().equals("Bisexual"))) ||
                                (user.getSexualOrientation().equals("Straight") && (userProfile.getGender().equals("Female") && (userProfile.getSexualOrientation().equals("Straight") || userProfile.getSexualOrientation().equals("Bisexual"))) ||
                                        (user.getSexualOrientation().equals("Bisexual") &&  (userProfile.getSexualOrientation().equals("Bisexual") || userProfile.getSexualOrientation().equals("Gay"))));
                    } else  {
                        return userProfile.getGender().equals("Female") &&
                                (user.getSexualOrientation().equals("Lesbian") && (userProfile.getSexualOrientation().equals("Lesbian") || userProfile.getSexualOrientation().equals("Bisexual"))) ||
                                (user.getSexualOrientation().equals("Straight") && (userProfile.getGender().equals("Male") && (userProfile.getSexualOrientation().equals("Straight") || userProfile.getSexualOrientation().equals("Bisexual"))) ||
                                        (user.getSexualOrientation().equals("Bisexual") &&  (userProfile.getSexualOrientation().equals("Bisexual") || userProfile.getSexualOrientation().equals("Lesbian"))));
                    }
                })
                .collect(Collectors.toList());

    }

    @Override
    public String saveRightSwipes(SwipeRecord swipeRecord) {
        Optional<SwipeRecord> swipeRecord1= swipeRecordRepo.findByUserEmailAndUserEmailLiked(swipeRecord.getUserEmailLiked(),swipeRecord.getUserEmail());
        if(swipeRecord1.isPresent()){
            MatchRecord matchRecord= MatchRecord.builder()
                    .user1(swipeRecord.getUserEmailLiked()).user2(swipeRecord.getUserEmail()).build();
            matchRecordRepo.save(matchRecord);
            swipeRecordRepo.deleteById(swipeRecord1.get().getId());
        }
        else {
            swipeRecordRepo.save(swipeRecord);
        }
        return "Record saved";
    }

    @Override
    public UserProfile updateUserProfile(UserProfile userProfile, String email) {
        UserProfile userProfile1 = repo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userProfile.setId(userProfile1.getId());
        userProfile.setEmail(userProfile1.getEmail());
        return repo.save(userProfile);

    }
}
