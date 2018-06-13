package com.devglan.service;

import com.devglan.model.Note;
import com.devglan.model.NoteDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {

    Note create(NoteDto note);
    boolean delete(long NoteId);
    List<Note> getNotes(long UserId);
    List<Note> findAllDone();
    Note update(NoteDto note);
}
