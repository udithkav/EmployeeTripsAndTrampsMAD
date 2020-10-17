package com.example.employeetripsandtramps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText email,password;
    SessionManager sessionManager;
    Button login;
    FirebaseAuth firebaseAuth;
    public static final String EXTRA_MESSAGE = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        email = (EditText) findViewById(R.id.inputEmail);
        password = (EditText) findViewById(R.id.inputPassword);
        login = (Button) findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email_value = email.getText().toString().trim();
                String password_value = password.getText().toString().trim();
                if(TextUtils.isEmpty(email_value)){
                    Toast.makeText(Login.this, "Please Enter Email",Toast.LENGTH_LONG);
                    return;
                }
                if(TextUtils.isEmpty(password_value)){
                    Toast.makeText(Login.this, "Please Enter Password",Toast.LENGTH_LONG);
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email_value, password_value)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Authentication Sucess.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    intent.putExtra(EXTRA_MESSAGE,email_value);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }


                            }
                        });
            }

        });
    }
}