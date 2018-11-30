package com.example.vectorism.compilation;

public class User {

    private String idUser;
    private int idImage=0;
    private String username;
    private String email;
    private String password;

    public User(String idUser, String username, String email, String password) {
        this.idUser = idUser;
        this.idImage = 0;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
