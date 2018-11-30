package com.example.vectorism.compilation;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText email;
    EditText password;
    EditText username;
    EditText password2;
    Button reg_button;
    ProgressDialog p_dialog;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.l_email);
        password = (EditText) findViewById(R.id.l_password);
        username = (EditText) findViewById(R.id.l_username);
        password2 = (EditText) findViewById(R.id.l_conpassword);
        reg_button = (Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(this);
        p_dialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {
        if (v == reg_button) {
            Register();
        }
    }

    private void Register(){
        final String e = email.getText().toString().trim();
        final String p = password.getText().toString().trim();
        final String u = username.getText().toString().trim();
        final String p2 = password2.getText().toString().trim();
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
        if (TextUtils.isEmpty(p2)) {
            Log.d("PASSWORD", "Empty");
            Toast.makeText(this, "Please Input the Confirmation Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(p!=p2){
            Log.d("PASSWORD", "Different");
            Toast.makeText(this, "Please Input the Same Password on Confirmation Password", Toast.LENGTH_SHORT).show();
            return;
        }
        p_dialog.setMessage("Registering User...");
        p_dialog.show();
        auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                p_dialog.dismiss();
                if (task.isComplete()) {
                    Toast.makeText(Register.this, "Registered Complete", Toast.LENGTH_SHORT).show();
                    User user = new User(u,e,p);
                    backToLogin();
                } else {
                    Toast.makeText(Register.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void backToLogin(){
        finish();
    }

}
