package com.example.vectorism.compilation;

public class ProfileController {
    private static Boolean open_edit = false;

    public static Boolean getOpen_edit() {
        return open_edit;
    }

    public static void setOpen_edit(Boolean open_edit) {
        ProfileController.open_edit = open_edit;
    }
}
