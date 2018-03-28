package com.devglan.dao;

import com.devglan.model.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteDao extends CrudRepository<Note, Long> {
}
