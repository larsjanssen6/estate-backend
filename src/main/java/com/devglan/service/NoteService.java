package com.devglan.service;

import com.devglan.model.Note;
import com.devglan.model.NoteDto;

import java.util.List;

public interface NoteService {

    Note create(NoteDto note);
    boolean delete(long NoteId);
    List<Note> getNotes(long UserId);
    List<Note> findAll();


}
