package com.example.capstone.design;

public class Member {
    private final static double DEFAULT_LAT = 35.886903;
    private final static double DEFAULT_LNG = 128.608485;

    private final static int PERMISSION_NORMAL_USER = 1000;
    private final static int PERMISSION_ADMIN = 2016118246;

    private String email;               // 이메일
    private String name;                // 이름
    private String password;            // 비밀번호
    private String nation;              // 국가
    private int admin;                  // Admin 여부. 기본 권한은 PERMISSION_NORMAL_USER
    private double lat;                 // 좌표(latitute)
    private double lng;                 // 좌표(longitute)

    public Member() {
        lat = DEFAULT_LAT;
        lng = DEFAULT_LNG;
        admin = PERMISSION_NORMAL_USER;
    }

    public Member(String email, String name, String password, String nation) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.nation = nation;
        this.admin = PERMISSION_NORMAL_USER;
        lat = DEFAULT_LAT;
        lng = DEFAULT_LNG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
