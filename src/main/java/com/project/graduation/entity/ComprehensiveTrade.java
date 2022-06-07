package com.project.graduation.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class ComprehensiveTrade {
    private int fromUserId;

    private String fromUserName;

    private int toUserId;

    private String toUserName;

    private int workId;

    private double price;

    private Date tradeDate;

    public ComprehensiveTrade(){

    }

    public ComprehensiveTrade(Trade t, String fromUserName, String toUserName){
        this.fromUserId = t.getFromUserId();
        this.toUserId = t.getToUserId();
        this.workId = t.getWorkId();
        this.price = t.getPrice();
        this.tradeDate = t.getTradeDate();
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
    }
}
