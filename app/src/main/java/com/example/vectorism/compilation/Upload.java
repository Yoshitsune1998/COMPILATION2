package com.example.vectorism.compilation;

public class Upload {
    private String description;
    private String title;
    private String img_url;
    public Upload(){

    }
    public Upload(String description,String title, String img_url){
        this.description=description;
        this.title=title;
        this.img_url=img_url;
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
}
