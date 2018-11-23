package com.example.capstone.design;

public class CommentItem {
    private int resAvatar;
    private String strMessage;
    private String strName;
    private String strDate;

    public CommentItem(int resAvatar, String strMessage, String strName, String strDate) {
        this.resAvatar = resAvatar;
        this.strMessage = strMessage;
        this.strName = strName;
        this.strDate = strDate;
    }

    public int getResAvatar() {
        return resAvatar;
    }

    public void setResAvatar(int resAvatar) {
        this.resAvatar = resAvatar;
    }

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
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
