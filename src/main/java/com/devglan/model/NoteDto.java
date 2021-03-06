package com.devglan.model;

import java.sql.Date;

public class NoteDto {

    private long note_id;
    private long user_id;
    private User potential_member;
    private Date date_created;
    private String content;
    private Date start;
    private Date end;
    private String done;

    public long getId() {
        return note_id;
    }

    public void setId(long id) {
        this.note_id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public User getPotential_member() {
        return potential_member;
    }

    public void setPotential_member(User potential_member) {
        this.potential_member = potential_member;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public void setStart(Date date) {
        this.start = date;
    }

    public void setEnd(Date date) {
        this.end = date;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getDone() {
        return done;
    }
}
