package com.example.capstone.design;

public class CommunityItem {
    private int resAvatar;
    private int resPicture;
    private String strDescription;
    private String strName;
    private String strDate;

    public CommunityItem(int resAvatar, int resPicture, String strDescription, String strName, String strDate) {
        this.resAvatar = resAvatar;
        this.resPicture = resPicture;
        this.strDescription = strDescription;
        this.strName = strName;
        this.strDate = strDate;
    }

    public int getResAvatar() {
        return resAvatar;
    }

    public void setResAvatar(int resAvatar) {
        this.resAvatar = resAvatar;
    }

    public int getResPicture() {
        return resPicture;
    }

    public void setResPicture(int resPicture) {
        this.resPicture = resPicture;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
}
