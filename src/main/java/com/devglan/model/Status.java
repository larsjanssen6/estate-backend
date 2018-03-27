package com.devglan.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(unique=true)
    private String content;
    @Column
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        java.sql.Date dateNow = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        if (this.date != null) {
            if (content == "$INT%")
                if (dateNow.before(this.date)) {
                    long day = ChronoUnit.DAYS.between(LocalDate.parse(dateNow.toString()), LocalDate.parse(this.date.toString()));
                    return "geïnteresseerd over " + day + " dag(en).";
                } else {
                    return "geïnteresseerd op dit moment";
                }
        }
        return content;
    }

    public void setContent(String content) {
        if (date != null) {
            this.content = "$INT%";
        }
        else {
            this.content = content;
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.content = "$INT%";
    }
}
