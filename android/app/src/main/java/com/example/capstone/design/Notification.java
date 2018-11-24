package com.example.capstone.design;

public class Notification {
    private String Content;
    private String Date;
    private String NoticeTitle;

    public Notification(){

    }
    public Notification(String content, String date, String noticeTitle) {
        Content = content;
        Date = date;
        NoticeTitle = noticeTitle;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNoticeTitle() {
        return NoticeTitle;
    }

    public void setNoticeTitle(String NoticeTitle) {
        NoticeTitle = NoticeTitle;
    }
}
