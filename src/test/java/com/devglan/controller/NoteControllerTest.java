package com.devglan.controller;

import com.devglan.model.*;
import com.devglan.service.NoteService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private User userDto;
    private String token;
    @Autowired
    private NoteService noteService;
    private Note noteDto;

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

        NoteDto testNote1 = new NoteDto();
        NoteDto testNote2 = new NoteDto();
        testNote1.setContent("test for specific notes");
        testNote2.setContent("test for specific notes again");
        testNote1.setUser_id(userDto.getId());
        testNote2.setUser_id(userDto.getId());
        noteService.create(testNote1);
        noteService.create(testNote2);
    }

    @Test
    public void createNote() throws Exception{
        NoteDto testNote = new NoteDto();
        testNote.setContent("test zin voor database en unittesting");
        Gson gson = new Gson();
        String json = gson.toJson(testNote);
        this.mockMvc.perform(post("/note/create").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getSpecificNotes() throws Exception{
        this.mockMvc.perform(post("/note/notes").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }

    @Test
    public void deleteNote() throws Exception{
        NoteDto testNote1 = new NoteDto();
        testNote1.setContent("This note is destined for destruction");
        testNote1.setUser_id(userDto.getId());
        Note deleteNote = noteService.create(testNote1);
        this.mockMvc.perform(post("/note/delete/" + deleteNote.getId()).header("Authorization", "Bearer " + token)).andExpect(status().isOk());
    }

    @Test
    public void getAllNotes() throws Exception{
        this.mockMvc.perform(post("/note/allnotes").header("Authorization", "Bearer " + token)).andExpect(status().isOk()).andExpect(jsonPath("$").isArray());
    }




    @After
    public void deleteLeftOvers()
    {
        this.userService.delete(this.userDto.getId());
    }

}
