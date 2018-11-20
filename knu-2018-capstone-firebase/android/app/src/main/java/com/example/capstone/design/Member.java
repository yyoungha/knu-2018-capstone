package com.example.capstone.design;

public class Member {
    private String WriterId;
    private String Name;
    private String StudentId;
    private String Password;
    private String Nation;
    private String Major;
    private String Image;
    private String Admin;

    public String getWriterId() {
        return WriterId;
    }

    public void setWriterId(String WriterId) {
        WriterId = WriterId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String StudentId) {
        StudentId = StudentId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String admin) {
        Admin = admin;
    }
}
