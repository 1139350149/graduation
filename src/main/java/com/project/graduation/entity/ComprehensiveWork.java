package com.project.graduation.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class ComprehensiveWork {
    private int workId;
    private String key;
    private int artistId;
    private String artistName;
    private double width;
    private double height;
    private String material;
    private int ownerId;
    private String ownerName;
    private String name;
    private Date time;
    private String remark;
    private String base64Img;
    private boolean forSale;
    private double price;

    public ComprehensiveWork(Work work, User artist, User owner, String base64Img) {
        this.workId = work.getId();
        this.key = work.getKey();
        if (artist != null) {
            this.artistId = artist.getId();
            this.artistName = artist.getName();
        }
        this.width = work.getWidth();
        this.height = work.getHeight();
        this.material = work.getMaterial();
        if (owner != null) {
            this.ownerId = owner.getId();
            this.ownerName = owner.getName();
        }
        this.name = work.getName();
        this.time = work.getTime();
        this.remark = work.getRemark();
        this.base64Img = base64Img;
        this.forSale = work.isForSale();
        this.price = work.getPrice();
    }

    public ComprehensiveWork(int workId, String base64Img) {
        this.workId = workId;
        this.base64Img = base64Img;
    }

    public ComprehensiveWork(Work work, String base64Img) {
        this.artistId = work.getArtistId();
        this.ownerId = work.getOwnerId();
        this.workId = work.getId();
        this.base64Img = base64Img;
        this.forSale = work.isForSale();
        this.price = work.getPrice();
    }
}
