package com.socials.UserProfile.services;

import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.repository.UserProfileRepo;
import com.socials.UserProfile.service.UserProfileServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class UserProfileService {

    @Mock
    private UserProfileRepo userProfileRepo;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    public void saveUserProfile_Test(){
        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
        dob(new Date(2000,9,01))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        Mockito.when(userProfileRepo.save(Mockito.any(UserProfile.class))).thenReturn(userProfile);

        UserProfile userProfile1= userProfileService.saveUserProfile(userProfile,"avarmittal1sep@gmail.com");

        Assertions.assertThat(userProfile1).isNotNull();

    }

    @Test
    public void getUserProfileByEmail_Test(){
        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
                dob(new Date(2000,9,01))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        Mockito.when(userProfileRepo.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(userProfile));

        UserProfile userProfile1= userProfileService.getUserProfileByEmail("avarmittal@gmail.com");

        Assertions.assertThat(userProfile1).isNotNull();

    }

}
