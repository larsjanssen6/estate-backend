package com.devglan.controller;

import com.devglan.model.Note;
import com.devglan.model.NoteDto;
import com.devglan.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public Note createNote(@RequestBody NoteDto note){
        return noteService.create(note);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public boolean deleteNote(@PathVariable (value = "id")long noteId){
        return noteService.delete(noteId);
    }

    @RequestMapping(value = "/notes/{user_id}", method = RequestMethod.POST)
    public List<Note> getNotes(@PathVariable (value = "user_id")long user_id){
        return noteService.getNotes(user_id);
    }


}
