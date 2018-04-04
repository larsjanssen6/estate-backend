package com.devglan.controller;

import com.devglan.model.Note;
import com.devglan.model.NoteDto;
import com.devglan.service.NoteService;
import com.devglan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public Note createNote(@RequestBody NoteDto note)
    {
        note.setId(userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        return noteService.create(note);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public boolean deleteNote(@PathVariable (value = "id")long noteId){
        return noteService.delete(noteId);
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public List<Note> getNotes(){
        return noteService.getNotes(userService.findOne(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
    }
    @RequestMapping(value = "/allnotes", method = RequestMethod.POST)
    public List<Note> getAllNotes(){
        return noteService.findAll();
    }


}
