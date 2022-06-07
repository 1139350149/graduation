package com.project.graduation.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "worktag")
@Data
@DynamicUpdate
public class WorkTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "workid")
    private int workId;

    public WorkTag() {

    }

    public WorkTag(String tag, int workId) {
        this.tag = tag;
        this.workId = workId;
    }
}
