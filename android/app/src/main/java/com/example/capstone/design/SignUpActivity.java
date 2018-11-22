package com.example.capstone.design;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText email_join;
    private EditText pwd_join;
    private EditText pwdchk_join;
    private EditText username_join;
    private EditText nation_join;

    private Button btn;
    private Button cancel_btn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_join = (EditText) findViewById(R.id.sign_up_email);
        pwd_join = (EditText) findViewById(R.id.sign_up_pwd);
        pwdchk_join = (EditText) findViewById(R.id.sign_up_pwdchk);
        username_join = (EditText) findViewById(R.id.sign_up_username);
        nation_join = (EditText) findViewById(R.id.sign_up_nation);

        btn = (Button) findViewById(R.id.enroll_btn);
        cancel_btn = (Button) findViewById(R.id.btn_cancel);

        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_join.getText().toString().trim();
                String pwd = pwd_join.getText().toString().trim();
                String pwdchk = pwdchk_join.getText().toString().trim();
                String username = username_join.getText().toString().trim();
                String nation = nation_join.getText().toString().trim();
                final Member member = new Member(email, pwd, username, nation);

                firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseDatabase.getInstance().getReference("Member")
                                            .child(firebaseAuth.getCurrentUser().getUid())
                                            .setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUpActivity.this, "사용자 등록 성공", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "사용자 등록 오류", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });


    }
}