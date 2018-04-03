package com.devglan.dao;

import com.devglan.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteDao extends CrudRepository<Note, Long> {
   // List<Note> findByUser_id(long user_id);

}
