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

import java.sql.Date;

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

        long now = System.currentTimeMillis();
        // Add a few days, maybe?
        Date dt = new Date(now);
        userDto.setInterestdate(dt);

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
    public void authenticationGetUsersIsOk() throws Exception {
        this.mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void authenticationGetUsersIsFalse() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void authenticationGetUserIsOk() throws Exception {
        this.mockMvc.perform(get("/users/"+ this.userDto.getId()).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void authenticationGetUserIsFalse() throws Exception {
        this.mockMvc.perform(get("/users/" + this.userDto.getId())).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void authenticationSignUpIsOk() throws Exception {
        UserDto testUser = new UserDto();
        testUser.setUsername("testUser8569");
        testUser.setPassword("testUser8569");
        createdUser = "testUser8569";
        Gson gson = new Gson();
        String json = gson.toJson(testUser);
        this.mockMvc.perform(post("/signup").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void authenticationSignUpIsFalse() throws Exception {
        UserDto testUser = new UserDto();
        Gson gson = new Gson();
        String json = gson.toJson(testUser);
        this.mockMvc.perform(post("/signup").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void getUsers() throws Exception {
        this.mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }
    @Test
    public void GetUser() throws Exception {
        this.mockMvc.perform(get("/users/" + this.userDto.getId()).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }
    @Test
    public void deleteUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestDeleteUser");
        userDto.setPassword("UnitTestPassword");
        User deleteUser = userService.save(userDto);
        this.mockMvc.perform(post("/users/deleteuser/" + deleteUser.getId()).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }
    @Test
    public void editUser() throws Exception {
        UserDto editUser = new UserDto();
        editUser.setId(this.userDto.getId());
        editUser.setUsername(this.userDto.getUsername());
        editUser.setPassword(this.userDto.getPassword());
        editUser.setRole(this.userDto.getRole());
        Gson gson = new Gson();
        String json = gson.toJson(editUser);
        this.mockMvc.perform(post("/update-user").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk());
    }


    @Test
    public void promoteUsersIsOk() throws Exception {
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
        userDto.setRole(Role.Admin);
        userDto.setId(this.userDto.getId());
        userService.update(userDto);
        this.mockMvc.perform(post("/users/promoteuser").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void promoteUsersIsFalse() throws Exception {
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

    @Test
    public void editStatus() throws Exception {
        UserDto editStatus = new UserDto();
        editStatus.setId(this.userDto.getId());
        editStatus.setUsername(this.userDto.getUsername());
        editStatus.setPassword(this.userDto.getPassword());
        editStatus.setRole(this.userDto.getRole());
        editStatus.setInterestdate(this.userDto.getInterestdate());
        Gson gson = new Gson();
        String json = gson.toJson(editStatus);
        this.mockMvc.perform(post("/update-user").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk());
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
