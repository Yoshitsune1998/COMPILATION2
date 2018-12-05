package com.example.vectorism.compilation;

public class Comment {
    private String UID;
    private String comment;

    public Comment(String UID, String comment) {
        this.UID = UID;
        this.comment = comment;
    }

    public Comment(){

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
