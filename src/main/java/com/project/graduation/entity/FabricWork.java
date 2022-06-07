package com.project.graduation.entity;

import lombok.Data;

@Data
public class FabricWork {
    private String key;
    private String time;
    private String name;
    private String artist;
    private String owner;
    private String width;
    private String height;
    private String material;

    public FabricWork() {

    }

    public FabricWork(String key, String time, String name, String artist, String owner, String width, String height, String material) {
        this.key = key;
        this.time = time;
        this.name = name;
        this.artist = artist;
        this.owner = owner;
        this.width = width;
        this.height = height;
        this.material = material;
    }
}
