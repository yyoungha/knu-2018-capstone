package com.example.capstone.design;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    final static String EMAIL="@knu.ac.kr";
    private EditText edit_EAMIL;
    private EditText edit_PWD;
    private Button btn_SIGNIN;
    private Button btn_SIGNUP;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        this.initializeValues();
        this.addListener();

    }
    private void initializeValues() {
        edit_EAMIL = (EditText) findViewById(R.id.sign_in_email);
        edit_PWD = (EditText) findViewById(R.id.sign_in_pwd);
        btn_SIGNIN = (Button) findViewById(R.id.sign_in_btn);
        btn_SIGNUP = (Button) findViewById(R.id.sign_up_btn);
        firebaseAuth = firebaseAuth.getInstance();
    }

    private void addListener() {
        //로그인 인증
        btn_SIGNIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = edit_EAMIL.getText().toString().trim();
                ID.concat(EMAIL); //기존의 로그인 계정 뒤에 이메일을 자동으로 붙여줌 id@knu.ac.kr
                String PASSWORD = edit_PWD.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(ID, PASSWORD)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignInActivity.this, "LOGIN ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        //리스너 장착
        btn_SIGNUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
