package com.devglan.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devglan.model.LoginUser;
import com.google.gson.Gson;
import org.json.JSONObject;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String token;

    @Test
    public void AuthenticationGetUsersIsOk() throws Exception {
        setToken();
        this.mockMvc.perform(get("/users").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void AuthenticationGetUsersIsFalse() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void AuthenticationGetUserIsOk() throws Exception {
        setToken();
        this.mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void AuthenticationGetUserIsFalse() throws Exception {
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    public void GetUsers() throws Exception {
        setToken();
        this.mockMvc.perform(get("/users").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }
    @Test
    public void GetUser() throws Exception {
        setToken();
        this.mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isNotEmpty());
    }

    private void setToken() throws Exception {
        LoginUser user = new LoginUser();
        user.setUsername("root");
        user.setPassword("Wachtwoord2");
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
}
