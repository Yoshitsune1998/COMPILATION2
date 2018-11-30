package com.example.vectorism.compilation;

import java.util.Calendar;
import java.util.Date;

public class Post {
    private String description;
    private String title;
    private String img_url;
    private Calendar post_date;
    public Post(){

    }
    public Post(String description,String title){
        this.description=description;
        this.title=title;
    }

    public String getDescription() {
        return description;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getTitle() {
        return title;
    }

    public Calendar getPost_date() {
        return post_date;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setPost_date(Calendar post_date) {
        this.post_date = post_date;
    }
}
