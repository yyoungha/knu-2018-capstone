package com.example.capstone.design;

public class Member {
    private static final int PERMISSION_NORMAL_USER = 1000;
    private static final int PERMISSION_ADMIN = 2016118246;

    public Member() {
    }

    private String email;
    private String pwd;
    private String pwd_chk;
    private String name;
    private String nation;
    private double lat;
    private double lng;
    private int permission;

    public Member(String email, String pwd, String name, String nation) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nation = nation;
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

    public String getPwd_chk() {
        return pwd_chk;
    }

    public void setPwd_chk(String pwd_chk) {
        this.pwd_chk = pwd_chk;
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
}
