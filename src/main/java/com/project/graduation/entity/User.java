package com.project.graduation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", length = 20, unique = true)
    private String name;

    @Column(name="password", length = 30)
    private String password;

    @Column(name="address")
    private String address;

    public User() {}

    public User(String name, String password, String address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }
}
