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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private User userDto;
    @Before
    public void register(){
        UserDto userDto = new UserDto();
        userDto.setUsername("UnitTestUser");
        userDto.setPassword("UnitTestPassword");
        userDto.setRole(Role.Admin);
        this.userDto = userService.save(userDto);
    }
    @Test
    public void AuthenticationIsOk() throws Exception {
        LoginUser user = new LoginUser();
        user.setUsername(this.userDto.getUsername());
        user.setPassword("UnitTestPassword");
        user.setUsername("sven");
        user.setPassword("sven");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        this.mockMvc.perform(post("/token/generate-token")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void AuthenticationIsFalse() throws Exception {
        LoginUser user = new LoginUser();
        user.setUsername(this.userDto.getUsername());
        user.setPassword("wrongPassword");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        this.mockMvc.perform(post("/token/generate-token")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is(401));
    }
    @After
    public void deleteUser()
    {
        this.userService.delete(this.userDto.getId());
    }

}

