package com.example.vectorism.compilation;

public class AccountController {

    private static CompUser _user = null;

    public static void nullAccount(){
        _user = null;
    }

    public static CompUser getUser(){
        return _user;
    }

    public static void setUser(CompUser user){
        _user = user;
    }

}
