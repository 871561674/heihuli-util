package com.heihuli.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author heihuli
 */
public class JacksonObj {
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", timezone = "GMT+8")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
