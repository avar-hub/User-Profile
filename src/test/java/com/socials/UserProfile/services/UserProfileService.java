package com.socials.UserProfile.services;

import com.socials.UserProfile.entity.MatchRecord;
import com.socials.UserProfile.entity.SwipeRecord;
import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.repository.MatchRecordRepo;
import com.socials.UserProfile.repository.SwipeRecordRepo;
import com.socials.UserProfile.repository.UserProfileRepo;
import com.socials.UserProfile.service.UserProfileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@ExtendWith(MockitoExtension.class)
public class UserProfileService {

    @Mock
    private UserProfileRepo userProfileRepo;

    @Mock
    private SwipeRecordRepo swipeRecordRepo;

    @Mock
    private MatchRecordRepo matchRecordRepo;
    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    @Test
    public void saveUserProfile_Test(){
        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
        dob(LocalDate.of(2000 - 1900, 9 - 1, 1))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        Mockito.when(userProfileRepo.save(Mockito.any(UserProfile.class))).thenReturn(userProfile);

        UserProfile userProfile1= userProfileService.saveUserProfile(userProfile,"avarmittal1sep@gmail.com");

        Assertions.assertEquals(userProfile1,userProfile);

    }

    @Test
    public void getUserProfileByEmail_Test(){
        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
                dob(LocalDate.of(2000 - 1900, 9 - 1, 1))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        Mockito.when(userProfileRepo.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(userProfile));

        UserProfile userProfile1= userProfileService.getUserProfileByEmail("avarmittal@gmail.com");

        Assertions.assertEquals(userProfile1,userProfile);

    }

    @Test
    public void deleteUserProfileByEmail_Test(){
        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
                dob(LocalDate.of(2000 - 1900, 9 - 1, 1))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        Mockito.when(userProfileRepo.findByEmail("avar@gmail.com")).thenReturn(Optional.of(userProfile));

        String s= userProfileService.deleteUserProfileByEmail("avar@gmail.com");

        Assertions.assertEquals("User deleted successfully",s);

    }

    @Test
    public void updateUserProfile_Test(){
        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
                dob(LocalDate.of(2000 - 1900, 9 - 1, 1))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        Mockito.when(userProfileRepo.findByEmail(Mockito.any(String.class))).thenReturn(Optional.of(userProfile));

        Mockito.when(userProfileRepo.save(userProfile)).thenReturn(userProfile);

        UserProfile userProfile1= userProfileService.updateUserProfile(userProfile,"avar@gmail.com");

        Assertions.assertEquals(userProfile1,userProfile);
    }

    @Test
    public void saveRightSwipes_Test(){
        SwipeRecord swipeRecord= SwipeRecord.builder().id(1).userEmail("abc@gmail.com")
                .userEmailLiked("ab@gmail.com")
                .build();

        Mockito.when(swipeRecordRepo.findByUserEmailAndUserEmailLiked(swipeRecord.getUserEmailLiked()
                        ,swipeRecord.getUserEmail())).thenReturn(Optional.of(swipeRecord));

        String s= userProfileService.saveRightSwipes(swipeRecord);

        Mockito.verify(matchRecordRepo).save(Mockito.any(MatchRecord.class));
        Mockito.verify(swipeRecordRepo).deleteById(Mockito.any());

        Assertions.assertEquals("Record saved",s);
    }

    @Test
    public void saveRightSwipes_NotAMatch_Test(){
        SwipeRecord swipeRecord= SwipeRecord.builder().id(1).userEmail("abc@gmail.com")
                .userEmailLiked("ab@gmail.com")
                .build();

        Mockito.when(swipeRecordRepo.findByUserEmailAndUserEmailLiked(swipeRecord.getUserEmailLiked()
                ,swipeRecord.getUserEmail())).thenReturn(Optional.empty());

        String s= userProfileService.saveRightSwipes(swipeRecord);

        Mockito.verify(swipeRecordRepo).save(Mockito.any(SwipeRecord.class));
        Assertions.assertEquals("Record saved",s);
    }

    @Test
    public void showPeople_Male_Straight_Test(){
        String email= "avarmittal@gmail.com";
        String city= "dehradun";
        UserProfile userProfile = UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email(email).gender("Male")
                .sexualOrientation("Straight").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();

        UserProfile userProfile1= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abc@gmail.com").gender("Female")
                .sexualOrientation("Straight").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        List<UserProfile> userProfiles= List.of(userProfile1);

        Mockito.when(userProfileRepo.findByEmail(email)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepo.findAllByCity(city)).thenReturn(userProfiles);

        List<UserProfile> userProfiles2= userProfileService.showPeople(email);

        Assertions.assertEquals(userProfiles2,userProfiles);
    }

    @Test
    public void showPeople_Female_Straight_Test(){
        String email= "avarmittal@gmail.com";
        String city= "dehradun";
        UserProfile userProfile = UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email(email).gender("Female")
                .sexualOrientation("Straight").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();

        UserProfile userProfile1= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abc@gmail.com").gender("Male")
                .sexualOrientation("Straight").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        List<UserProfile> userProfiles= List.of(userProfile1);

        Mockito.when(userProfileRepo.findByEmail(email)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepo.findAllByCity(city)).thenReturn(userProfiles);

        List<UserProfile> userProfiles2= userProfileService.showPeople(email);

        Assertions.assertEquals(userProfiles2,userProfiles);
    }
    @Test
    public void showPeople_Male_Gay__Test(){
        String email= "avarmittal@gmail.com";
        String city= "dehradun";
        UserProfile userProfile = UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email(email).gender("Male")
                .sexualOrientation("Gay").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();

        UserProfile userProfile1= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abc@gmail.com").gender("Male")
                .sexualOrientation("Gay").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        UserProfile userProfile2= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abcdd@gmail.com").gender("Male")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        List<UserProfile> userProfiles= List.of(userProfile1,userProfile2);

        Mockito.when(userProfileRepo.findByEmail(email)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepo.findAllByCity(city)).thenReturn(userProfiles);

        List<UserProfile> userProfiles2= userProfileService.showPeople(email);

        Assertions.assertEquals(userProfiles2,userProfiles);
    }
    @Test
    public void showPeople_Male_Bisexual_Test(){
        String email= "avarmittal@gmail.com";
        String city= "dehradun";
        UserProfile userProfile = UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email(email).gender("Male")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();

        UserProfile userProfile1= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abc@gmail.com").gender("Male")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        UserProfile userProfile2= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abcdd@gmail.com").gender("Male")
                .sexualOrientation("Gay").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        UserProfile userProfile3= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abcddee@gmail.com").gender("Female")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        List<UserProfile> userProfiles= List.of(userProfile1,userProfile2,userProfile3);

        Mockito.when(userProfileRepo.findByEmail(email)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepo.findAllByCity(city)).thenReturn(userProfiles);

        List<UserProfile> userProfiles2= userProfileService.showPeople(email);

        Assertions.assertEquals(userProfiles2,userProfiles);
    }
    @Test
    public void showPeople_Female_Lesbian_Test(){
        String email= "avarmittal@gmail.com";
        String city= "dehradun";
        UserProfile userProfile = UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email(email).gender("Female")
                .sexualOrientation("Lesbian").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();

        UserProfile userProfile1= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abc@gmail.com").gender("Female")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        UserProfile userProfile2= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abcdd@gmail.com").gender("Female")
                .sexualOrientation("Lesbian").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        List<UserProfile> userProfiles= List.of(userProfile1,userProfile2);

        Mockito.when(userProfileRepo.findByEmail(email)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepo.findAllByCity(city)).thenReturn(userProfiles);

        List<UserProfile> userProfiles2= userProfileService.showPeople(email);

        Assertions.assertEquals(userProfiles2,userProfiles);
    }
    @Test
    public void showPeople_Female_Bisexual_Test(){
        String email= "avarmittal@gmail.com";
        String city= "dehradun";
        UserProfile userProfile = UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email(email).gender("Female")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();

        UserProfile userProfile1= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abc@gmail.com").gender("Female")
                .sexualOrientation("Lesbian").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        UserProfile userProfile2= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abcdd@gmail.com").gender("Female")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        UserProfile userProfile3= UserProfile.builder().name("abc").city(city)
                .dob(LocalDate.of(2000 - 1900, 9 - 1, 1)).email("abcddee@gmail.com").gender("Male")
                .sexualOrientation("Bisexual").interests(List.of("Hiking", "Reading", "Cooking"))
                .build();
        List<UserProfile> userProfiles= List.of(userProfile1,userProfile2,userProfile3);

        Mockito.when(userProfileRepo.findByEmail(email)).thenReturn(Optional.of(userProfile));
        Mockito.when(userProfileRepo.findAllByCity(city)).thenReturn(userProfiles);

        List<UserProfile> userProfiles2= userProfileService.showPeople(email);

        Assertions.assertEquals(userProfiles2,userProfiles);
    }
}


