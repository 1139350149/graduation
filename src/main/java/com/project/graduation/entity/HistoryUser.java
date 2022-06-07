package com.project.graduation.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class HistoryUser {
    private int userId;

    private String name;

    private String address;

    private Date viewDate;

    public HistoryUser() {
    }

    public HistoryUser(User user, History history){
        this.userId = user.getId();
        this.name = user.getName();
        this.address = user.getAddress();
        this.viewDate = history.getViewDate();
    }
}
