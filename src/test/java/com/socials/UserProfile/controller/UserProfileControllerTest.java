package com.socials.UserProfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socials.UserProfile.entity.SwipeRecord;
import com.socials.UserProfile.entity.UserProfile;
import com.socials.UserProfile.service.UserProfileServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Date;
import java.util.List;

@WebMvcTest(controllers = UserProfileController.class)
//It will take care of security , we do not have to add tokens
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileServiceImpl userProfileService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void saveUser_Test() throws Exception {

        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
                dob(new Date(2000,9,01))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/profile/saveUser")
                .param("email","abc@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userProfile)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void getByEmail_Test() throws Exception {


        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.get("/profile/getByEmail")
                .param("email","abc@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void deleteByEmail_Test() throws Exception {

        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.delete("/profile/deleteByEmail")
                .param("email","abc@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void showPeople_Test() throws Exception {

        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.get("/profile/getPeopleNearByMe")
                .param("email","abc@gmail.com"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void swipeRight_Test() throws Exception {

        SwipeRecord swipeRecord= SwipeRecord.builder().userEmail("abc@gmail.com").userEmailLiked("bcd@gmail.com").build();

        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/profile/swipeRight")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(swipeRecord)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void updateUserProfile_Test() throws Exception {

        UserProfile userProfile= UserProfile.builder().name("abc").city("dehradun").
                dob(new Date(2000,9,01))
                .gender("Male").sexualOrientation("Straight")
                .interests(List.of("Hiking","Reading","Cooking")).build();

        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.put("/profile/updateUser")
                .param("email","abc@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userProfile)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
