package com.devglan.controller;

import com.devglan.model.Note;
import com.devglan.model.NoteDto;
import com.devglan.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}
