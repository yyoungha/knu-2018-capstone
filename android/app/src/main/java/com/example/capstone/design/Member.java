package com.example.capstone.design;

public class Member {
    private static final int PERMISSION_NORMAL_USER = 1000;
    private static final int PERMISSION_ADMIN = 2016118246;

    private String uid;
    private String email;
    private String pwd;
    private String name;
    private String nation;
    private double lat;
    private double lng;
    private int permission;
    private String imageUri;
    private String token;

    public Member(String uid, String email, String pwd, String name, String nation, double lat, double lng, int permission, String imageUri) {
        this.uid = uid;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nation = nation;
        this.lat = lat;
        this.lng = lng;
        this.permission = permission;
        this.imageUri = imageUri;
    }

    public Member() {
    }

    public Member(String email, String pwd, String name, String nation) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nation = nation;
        this.imageUri = null;
        this.uid = null;
    }

    public Member(String email, String pwd, String name, String nation, String imageUri) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nation = nation;
        this.imageUri = imageUri;
        this.uid = null;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getimageUri() {
        return imageUri;
    }

    public void setimageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
