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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    EditText registration_email,registration_password;
    Button button_registration;
    TextView textView_login;
    String reg_email,reg_password;

    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initiateComponents();
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    private void initiateComponents() {
        registration_email = findViewById(R.id.email_registration);
        registration_password = findViewById(R.id.password_registration);
        button_registration = findViewById(R.id.register_registration);
        textView_login = findViewById(R.id.textView_reg);
        operationsOfRegistration();

    }

    private void operationsOfRegistration() {
        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        button_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_email = registration_email.getText().toString().trim();
                reg_password = registration_password.getText().toString().trim();

                if (TextUtils.isEmpty(reg_email)) {
                    registration_email.setError("Email Is Required");
                    return;
                }
                if (TextUtils.isEmpty(reg_email)) {
                    registration_password.setError("Password Is Required");
                    return;
                }

                progressDialog.setMessage("Processing");
                progressDialog.setTitle("Office Management System");
                progressDialog.setIcon(R.drawable.ic_https_black_24dp);
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(reg_email,reg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            progressDialog.dismiss();
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });
            }
        });
    }
}
