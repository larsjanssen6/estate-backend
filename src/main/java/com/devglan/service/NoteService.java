package com.devglan.service;

import com.devglan.model.Note;
import com.devglan.model.NoteDto;

import java.util.List;

public interface NoteService {

    boolean create(NoteDto note);
    boolean delete(long NoteId);
    List<Note> getNotes(long UserId);


}
