package com.project.graduation.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "work")
@Data
@DynamicUpdate
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "keyid")
    private String key;

    @Column(name = "artistid")
    private int artistId;

    @Column(name = "width")
    private double width;

    @Column(name = "height")
    private double height;

    @Column(name = "material")
    private String material;

    @Column(name = "ownerid")
    private int ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private Date time;

    @Column(name = "remark")
    private String remark;

    @Column(name = "sale")
    private boolean forSale;

    @Column(name = "price")
    private double price;

    public Work() {

    }

    public Work(String key, int artistId, double width, double height, String material, int ownerId, String name, Date time, String remark) {
        this.key = key;
        this.artistId = artistId;
        this.width = width;
        this.height = height;
        this.material = material;
        this.ownerId = ownerId;
        this.name = name;
        this.time = time;
        this.remark = remark;
        this.forSale = false;
        this.price = -1.0;
    }
}
