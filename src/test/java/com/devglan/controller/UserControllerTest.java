package com.devglan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devglan.model.LoginUser;
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
    @Before
    public void register() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestUser123456789");
        userDto.setPassword("UnitTestPassword");
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
        this.mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void AuthenticationGetUserIsFalse() throws Exception {
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void AuthenticationSignUpIsOk() throws Exception {
        UserDto testUser = new UserDto();
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder userName = new StringBuilder();
        Random rnd = new Random();
        while (userName.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            userName.append(CHARS.charAt(index));
        }
        testUser.setUsername("testUser" + userName.toString());
        testUser.setPassword(userName.toString());
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
        this.mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }
    @Test
    public void DeleteUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestDeleteUser70384");
        userDto.setPassword("UnitTestPassword");
        User deleteUser = userService.save(userDto);
        this.mockMvc.perform(post("/users/deleteuser/" + deleteUser.getId()).header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }
    @After
    public void deleteLoginUser()
    {
        this.userService.delete(this.userDto.getId());
    }
}
