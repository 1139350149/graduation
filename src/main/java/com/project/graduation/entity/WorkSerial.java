package com.project.graduation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "serial")
@Data
@DynamicUpdate
@NoArgsConstructor
public class WorkSerial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sid")
    private int sid;

    @Column(name = "serial")
    private int workSerial;

    public WorkSerial(int sid, int workSerial) {
        this.sid = sid;
        this.workSerial = workSerial;
    }
}
