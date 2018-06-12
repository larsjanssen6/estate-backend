package com.devglan.controller;

import com.devglan.model.Note;
import com.devglan.model.Role;
import com.devglan.model.User;
import com.devglan.model.UserDto;
import com.devglan.service.NoteService;
import com.devglan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @CrossOrigin
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public User update(@RequestBody UserDto user){
        return userService.update(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/users/deleteuser/{id}", method = RequestMethod.POST)
    public boolean deleteUser(@PathVariable(value = "id") Long id) {return userService.delete(id);}

    @CrossOrigin
    @RequestMapping(value = "/users/promoteuser", method = RequestMethod.POST)
    public ResponseEntity<?> promoteUser(@RequestBody UserDto user) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString() == "Admin"){
            userService.removeAdmin(SecurityContextHolder.getContext().getAuthentication().getName());
            user.setRole(Role.Admin);
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }
    @CrossOrigin
    @RequestMapping(value="/user", method = RequestMethod.GET)
    public User getUserLoggedIn(){
        return userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @CrossOrigin
    @RequestMapping(value = "/users/notes", method = RequestMethod.POST)
    public List<User> getAllUsersForNotes(){
        long userid = userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        List<Note> notes = noteService.getNotes(userid);
        List<User> users = new ArrayList<>();
        for (Note n:notes
             ) {
            users.add(userService.findById(n.getPotential_member_id().getId()));
        }
        return users;
    }

    @CrossOrigin
    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }

}
