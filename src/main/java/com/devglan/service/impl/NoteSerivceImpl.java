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
            newNote.setPotentialMemberId(note.getPotential_member_id());
            newNote.setDate_created(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            newNote.setDone(note.getDone());
            newNote.setStart(note.getStart());
            newNote.setEnd(note.getEnd());
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
    public List<Note> findAllDone() {
        List<Note> list = new ArrayList<>();
        noteDao.findNotesByDone().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Note update(NoteDto note) {
        Note currNote = new Note();
        currNote.setId(note.getId());
        currNote.setUser_id(note.getUser_id());
        currNote.setContent(note.getContent());
        currNote.setPotentialMemberId(note.getPotential_member_id());
        currNote.setDate_created(note.getDate_created());
        currNote.setDone(note.getDone());
        currNote.setStart(note.getStart());
        currNote.setEnd(note.getEnd());
        return noteDao.save(currNote);
    }
}
