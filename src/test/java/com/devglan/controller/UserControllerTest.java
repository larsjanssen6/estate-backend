package com.devglan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devglan.model.LoginUser;
import com.devglan.model.Role;
import com.devglan.model.User;
import com.devglan.model.UserDto;
import com.devglan.service.UserService;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private User userDto;
    private String token;
    private String createdUser;
    @Before
    public void register() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestUser");
        userDto.setPassword("UnitTestPassword");
        userDto.setRole(Role.Admin);
        this.userDto = userService.save(userDto);
        LoginUser user = new LoginUser();
        user.setUsername(userDto.getUsername());
        user.setPassword("UnitTestPassword");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        MvcResult result = this.mockMvc.perform(post("/token/generate-token")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk()).andReturn();
        String jsonString = result.getResponse().getContentAsString();
        JSONObject root = new JSONObject(jsonString);
        this.token = root.getString("token");
        System.out.println("token: " + root.getString("token"));
    }
    @Test
    public void AuthenticationGetUsersIsOk() throws Exception {
        this.mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void AuthenticationGetUsersIsFalse() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void AuthenticationGetUserIsOk() throws Exception {
        this.mockMvc.perform(get("/users/"+ this.userDto.getId()).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void AuthenticationGetUserIsFalse() throws Exception {
        this.mockMvc.perform(get("/users/" + this.userDto.getId())).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void AuthenticationSignUpIsOk() throws Exception {
        UserDto testUser = new UserDto();
        testUser.setUsername("testUser8569");
        testUser.setPassword("testUser8569");
        createdUser = "testUser8569";
        Gson gson = new Gson();
        String json = gson.toJson(testUser);
        this.mockMvc.perform(post("/signup").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void AuthenticationSignUpIsFalse() throws Exception {
        UserDto testUser = new UserDto();
        Gson gson = new Gson();
        String json = gson.toJson(testUser);
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void GetUsers() throws Exception {
        this.mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }
    @Test
    public void GetUser() throws Exception {
        this.mockMvc.perform(get("/users/" + this.userDto.getId()).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }
    @Test
    public void DeleteUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestDeleteUser");
        userDto.setPassword("UnitTestPassword");
        User deleteUser = userService.save(userDto);
        this.mockMvc.perform(post("/users/deleteuser/" + deleteUser.getId()).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());

    }

    @Test
    public void PromoteUsersIsOk() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestPromoteUser");
        userDto.setPassword("UnitTestPassword");
        userDto.setRole(Role.Member);
        User user = userService.save(userDto);
        userDto.setId(user.getId());
        createdUser = "UnitTestPromoteUser";
        Gson gson = new Gson();
        String json = gson.toJson(userDto);
        this.mockMvc.perform(post("/users/promoteuser").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().is(200));

    }
    @Test
    public void PromoteUsersIsFalse() throws Exception {
        UserDto userPromote = new UserDto();
        userPromote.setUsername("UnitTestPromoteUser");
        userPromote.setPassword("UnitTestPassword");
        userPromote.setRole(Role.Member);
        User user = userService.save(userPromote);
        userPromote.setId(user.getId());
        Gson gson = new Gson();
        String json = gson.toJson(userPromote);
        this.createdUser = "UnitTestPromoteUser";
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestUser");
        userDto.setPassword("UnitTestPassword");
        userDto.setRole(Role.Member);
        userDto.setId(this.userDto.getId());
        userService.update(userDto);
        this.mockMvc.perform(post("/users/promoteuser").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().is(401));

    }


    @After
    public void deleteLoginUser()
    {
        if (createdUser != null){
            this.userService.delete(this.userService.findOne(createdUser).getId());
            createdUser = null;
        }
        this.userService.delete(this.userDto.getId());
    }
}
