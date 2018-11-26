package com.example.capstone.design;

public class CommunityWrite {

    private String Uid; // 글쓴이 식별용
    private String Title;
    private String Content;
    private String Date;
    private String url;
    private String object_info;

    public CommunityWrite(){

    }

    public CommunityWrite(String Uid,String Content,String Date){
        this.Uid = Uid;
        this.Content = Content;
        this.Date = Date;

    }

    public CommunityWrite(String Uid, String Title, String Content, String Date, String url){
        this.Uid = Uid;
        this.Title = Title;
        this.Content = Content;
        this.Date = Date;
        this.object_info=null;
        this.url = url;
    }


    public CommunityWrite(String Uid, String Title, String Content, String Date, String url,String object_info) {
        this.Uid = Uid;
        this.Title = Title;
        this.Content = Content;
        this.Date = Date;
        this.object_info=object_info;
        this.url = url;

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) { this.Uid = uid; }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getUrl() { return url; }

    public void setUrl(String url) {
        this.url = url;
    }

    public String toString(){
        return Date + " " + Uid + " " + url + " ";
    }

    public String getObjInfo(){
        return object_info;
    }

    public void setObject_info(String ob_info){
        this.object_info=ob_info;
    }




}
