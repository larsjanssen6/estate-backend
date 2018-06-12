package com.devglan.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long note_id;
    @Column
    private long user_id;
    @ManyToOne
    private User potential_member_id;
    @Column
    private Date date_created;
    @Column
    private Date start;
    @Column
    private Date end;
    @Lob
    @Column
    private String content;
    @Column
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

    public void setPotentialMemberId(User id) {
        this.potential_member_id = id;
    }

    public User getPotential_member_id() {
        return potential_member_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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
