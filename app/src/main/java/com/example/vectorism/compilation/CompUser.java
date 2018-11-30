package com.example.vectorism.compilation;

public class CompUser {

    public String urlImage;
    public String username;
    public String uID;

    public CompUser(String username,String uri,String uID) {
        this.urlImage = uri;
        this.uID = uID;
        this.username = username;
    }

    public CompUser() {
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
