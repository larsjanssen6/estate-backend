package com.devglan.service.impl;

import com.devglan.dao.NoteDao;
import com.devglan.model.Note;
import com.devglan.model.NoteDto;
import com.devglan.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service(value = "noteService")
public class NoteSerivceImpl  implements NoteService{
    @Autowired
    private NoteDao noteDao;


    @Override
    public Note create(NoteDto note) {
            Note newNote = new Note();
            newNote.setUser_id(note.getUser_id());
            newNote.setContent(note.getContent());
            newNote.setDate_created(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            return noteDao.save(newNote);
    }

    @Override
    public boolean delete(long NoteId) {
        try{
            noteDao.delete(NoteId);
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    @Override
    public List<Note> getNotes(long UserId) {
        List<Note> list = new ArrayList<>();
        noteDao.findNoteByUser_id(UserId).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public List<Note> findAll() {
        List<Note> list = new ArrayList<>();
        noteDao.findAll().iterator().forEachRemaining(list::add);
        //noteDao.findNotesByuser_id(UserId).addAll(list);
        return list;
    }
}
