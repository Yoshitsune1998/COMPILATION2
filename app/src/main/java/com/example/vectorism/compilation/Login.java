package com.example.vectorism.compilation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.*;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login_button;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.i_username);
        password = (EditText) findViewById(R.id.i_password);
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = username.getText().toString();
                String p = password.getText().toString();
                if (u != "" && p != "") {
                    checkLogin(u,p);
                }
            }
        });
    }

    private void checkLogin(String u, String p){

    }

}
