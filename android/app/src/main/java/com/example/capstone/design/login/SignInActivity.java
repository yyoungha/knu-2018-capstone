package com.example.capstone.design.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.design.MainActivity;
import com.example.capstone.design.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    final String EMAIL="@knu.ac.kr";
    private EditText email_join;
    private EditText pwd_join;
    private Button signin_btn;
    private Button signup_btn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        email_join = (EditText) findViewById(R.id.sign_in_email);
        pwd_join = (EditText) findViewById(R.id.sign_in_pwd);
        signin_btn = (Button) findViewById(R.id.sign_in_btn);
        signup_btn = (Button) findViewById(R.id.sign_up_btn);


        firebaseAuth = firebaseAuth.getInstance();
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_join.getText().toString().trim();
                email.concat(EMAIL);
                String pwd = pwd_join.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, "login error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
