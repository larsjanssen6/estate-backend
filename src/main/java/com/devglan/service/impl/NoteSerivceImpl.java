package com.devglan.service.impl;

import com.devglan.model.NoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "noteService")
public class NoteSerivceImpl {
    @Autowired
    private NoteDto noteDto;
    

}
