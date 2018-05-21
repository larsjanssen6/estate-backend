package com.devglan.model;

import java.sql.Date;

public class NoteDto {

    private long id;
    private long user_id;
    private long potential_member_id;
    private Date date_created;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setPotentialMemberId(long id) {
        this.potential_member_id = id;
    }

    public long getPotential_member_id() {
        return potential_member_id;
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
}
