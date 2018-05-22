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

import java.sql.Date;
import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateUser {

    @Autowired
    private UserService userService;

    @Test
    public void register() {
        UserDto userDto = new UserDto();
        ////als je wil mag je dit veranderen
        //userDto.setUsername("User");
        //userDto.setPassword("User");
        //userDto.setRole(Role.Admin);
        //userDto.setAddress("Straatweg 1");
        //userDto.setBirthdate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        //userDto.setCity("Tilburg");
        //userDto.setDate_joined(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        //userDto.setFirst_name("Rick");
        //userDto.setSurname("van Schijndel");
        //userDto.setZipcode("5000");
        //userDto.setProfession("ICT-er");
        //userDto.setInterestdate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        //userService.save(userDto);
    }
}