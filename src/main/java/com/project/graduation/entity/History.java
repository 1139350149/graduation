package com.project.graduation.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "history")
@Data
@DynamicUpdate
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userid")
    private int userId;

    @Column(name = "wordid")
    private int wordId;

    @Column(name = "isdelete")
    private boolean isDelete;

    @Column(name = "viewdate")
    private Date viewDate;

    public History() {

    }

    public History(int userId, int wordId) {
        this.userId = userId;
        this.wordId = wordId;
        this.isDelete = false;
        this.viewDate = new Date(new java.util.Date().getTime());
    }
}
