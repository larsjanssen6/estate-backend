package com.devglan.dao;

import com.devglan.model.Note;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteDao extends CrudRepository<Note, Long> {

    //List<Note> findNoteByUser_id(long user_id);

    @Query("SELECT n.content FROM note n where n.user_id = :user_id")
    List<Note> findTitleById(@Param("user_id") Long user_id);


    //List<Note> findNotesByuser_id(long user_id);
}
