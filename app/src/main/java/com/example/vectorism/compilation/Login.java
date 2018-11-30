package com.example.vectorism.compilation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText email;
    EditText password;
    Button login_button;
    Button reg_button;
    ProgressDialog p_dialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.i_email);
        password = (EditText) findViewById(R.id.i_password);
        login_button = (Button) findViewById(R.id.login_button);
        reg_button = (Button) findViewById(R.id.reg_log_button);
        login_button.setOnClickListener(this);
        reg_button.setOnClickListener(this);
        p_dialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if (v == login_button) {
            Login();
        } else if (v == reg_button) {
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        }
    }

    private void Login() {
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();
        if (TextUtils.isEmpty(e)) {
            Log.d("USERNAME", "Empty");
            Toast.makeText(this, "Please Input Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(p)) {
            Log.d("PASSWORD", "Empty");
            Toast.makeText(this, "Please Input Password", Toast.LENGTH_SHORT).show();
            return;
        }
        p_dialog.setMessage("Login...");
        p_dialog.show();
        auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                p_dialog.dismiss();
                if(task.isComplete()){
                    if(task.isSuccessful()){
                        changeToHome();
                    }else{
                        Toast.makeText(Login.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeToHome(){
        Intent intent = new Intent(this,DefaultLayout.class);
        this.finish();
        startActivity(intent);
    }

}