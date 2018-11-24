package com.example.capstone.design;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final static String EMAILADDRESS="@knu.ac.kr";
    private EditText email_join;
    private EditText pwd_join;
    private EditText pwdchk_join;
    private EditText username_join;
    private String NATION;
    private Button btn;
    private Button cancel_btn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.initalizeValues();
        this.addEventListener();

    }

    private void addEventListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL = email_join.getText().toString().trim(); EMAIL.concat(EMAILADDRESS);
                String PWD = pwd_join.getText().toString().trim();
                String PWDCHK = pwdchk_join.getText().toString().trim();
                String USERNAME = username_join.getText().toString().trim();
                final Member member = new Member(EMAIL, PWD, USERNAME, NATION);

                //예외처리
                if(EMAIL.equals("")||PWD.equals("")||USERNAME.equals("")){
                    Toast.makeText(SignUpActivity.this,"please fill blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(NATION.equals("▼")){
                    Toast.makeText(SignUpActivity.this,"please select nation", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!PWD.equals(PWDCHK)) {
                    Toast.makeText(SignUpActivity.this, "Did not matched PWD and PWCHK", Toast.LENGTH_LONG).show();
                    return;
                }


                firebaseAuth.createUserWithEmailAndPassword(EMAIL, PWD)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseDatabase.getInstance().getReference("Member")
                                            .child(firebaseAuth.getCurrentUser().getUid())
                                            .setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUpActivity.this, "User registration succeeded", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "User registration error", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initalizeValues() {
        //스피너
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //어댑터 생성
        //이 예제 같은 경우 string,xml에 리스트를 추가해 놓고 그 리스트를 불러온다.
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.nation_array, android.R.layout.simple_spinner_item);
        //스피너와 어댑터 연결
        spinner.setAdapter(adapter);
        //이벤트를 일으킨 위젯과 리스너와 연결
        spinner.setOnItemSelectedListener(this);
        //선택된 값 스트링으로 받기
        NATION = spinner.getSelectedItem().toString();
        email_join = (EditText) findViewById(R.id.sign_up_email);
        pwd_join = (EditText) findViewById(R.id.sign_up_pwd);
        pwdchk_join = (EditText) findViewById(R.id.sign_up_pwdchk);
        username_join = (EditText) findViewById(R.id.sign_up_username);
        btn = (Button) findViewById(R.id.enroll_btn);
        cancel_btn = (Button) findViewById(R.id.btn_cancel);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    //아이템 중 하나를 선택 했을때 호출되는 콜백 메서드
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position!=0){
            //Toast.makeText(this, "이미지"+(position)+" 클릭되었습니다", Toast.LENGTH_SHORT).show();

        }

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

}