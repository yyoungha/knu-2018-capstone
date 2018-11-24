package com.example.capstone.design;

public class Help {
    private String name;
    private String Uid;
    private String title;
    private String contents;
    private double lat;
    private double lng;

    public Help() {
    }

    public Help(String uid, String title, String contents, double lat, double lng) {
        name = Personal.getName();
        Uid = uid;
        this.title = title;
        this.contents = contents;
        this.lat = lat;
        this.lng = lng;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
