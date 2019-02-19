package com.vaibhav.managementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView textView_signup;
    EditText editText_email,editText_password;
    Button button_login_operation;
    FirebaseAuth firebaseAuth;
    ProgressDialog LprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateComponentsMain();
        LprogressDialog=new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        button_login_operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_email = editText_email.getText().toString().trim();
                String login_password = editText_password.getText().toString().trim();

                if (TextUtils.isEmpty(login_email)) {
                    editText_email.setError("Invalid Email-ID/Input");
                    return;
                }
                if (TextUtils.isEmpty(login_email)) {
                    editText_password.setError("Invalid Password-ID/Input");
                    return;
                }
                LprogressDialog.setMessage("Processing");
                LprogressDialog.setTitle("Office Management System");
                LprogressDialog.setIcon(R.drawable.ic_https_black_24dp);
                LprogressDialog.show();

               // firebaseauth.signInWithEmailAndPassword(login_email,login_password)
                firebaseAuth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            LprogressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            LprogressDialog.dismiss();
                        }
                    }
                });

            }
        });
        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });
    }

    private void initiateComponentsMain() {
        editText_email = findViewById(R.id.email_login);
        editText_password = findViewById(R.id.password_login);
        button_login_operation = findViewById(R.id.button_login);
        textView_signup = findViewById(R.id.txt_login);
    }

}
