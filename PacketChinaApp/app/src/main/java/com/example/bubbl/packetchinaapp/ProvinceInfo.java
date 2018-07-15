package com.example.bubbl.packetchinaapp;

public class ProvinceInfo {
    private int id;
    private String name;
    private String introURL;

    public ProvinceInfo(int id, String name, String introURL) {
        super();
        this.name = name;
        this.introURL = introURL;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getIntroURL() {
        return introURL;
    }

    public int getId() {
        return id;
    }
}
