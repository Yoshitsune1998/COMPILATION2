package com.example.vectorism.compilation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    startActivity(new Intent(SplashScreen.this,Login.class));
                }else{
                    startActivity(new Intent(SplashScreen.this,DefaultLayout.class));
                }
            }
        }, 2000);
    }
}
