package com.project.graduation.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "trade")
@Data
@DynamicUpdate
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fromuser")
    private int fromUserId;

    @Column(name = "touser")
    private int toUserId;

    @Column(name = "workid")
    private int workId;

    @Column(name = "price")
    private double price;

    @Column(name = "tradedate")
    private Date tradeDate;

    public Trade() {

    }

    public Trade(int fromUserId, int toUserId, int workId, double price, Date tradeDate) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.workId = workId;
        this.price = price;
        this.tradeDate = tradeDate;
    }
}
