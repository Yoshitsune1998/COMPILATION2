package com.example.vectorism.compilation;

public class BerandaController {

    private static int active_navbar = 3;

    public static void setActive_navbar(int index){
        active_navbar = index;
    }

    public static int getActive_navbar(){
        return active_navbar;
    }

}
