package com.example.vectorism.compilation;

public class Post {
    private String description;
    private String title;
    private String img_url;
    private String post_date;
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

    public String getPost_date() {
        return post_date;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }
}
