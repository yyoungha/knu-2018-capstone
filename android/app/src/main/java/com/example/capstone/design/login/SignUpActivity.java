package com.example.capstone.design.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstone.design.MainActivity;
import com.example.capstone.design.Member;
import com.example.capstone.design.Personal;
import com.example.capstone.design.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText email_join;
    private EditText pwd_join;
    private EditText pwdchk_join;
    private EditText username_join;
    //private EditText nation_join;

    private Button btn;
    private Button cancel_btn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //스피너
        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //어댑터 생성
        //이 예제 같은 경우 string,xml에 리스트를 추가해 놓고 그 리스트를 불러온다.
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.nation_array, android.R.layout.simple_spinner_item);
        //스피너와 어댑터 연결
        spinner.setAdapter(adapter);
        //이벤트를 일으킨 위젯과 리스너와 연결
        spinner.setOnItemSelectedListener(this);
        //선택된 값 스트링으로 받기



        email_join = (EditText) findViewById(R.id.sign_up_email);
        pwd_join = (EditText) findViewById(R.id.sign_up_pwd);
        pwdchk_join = (EditText) findViewById(R.id.sign_up_pwdchk);
        username_join = (EditText) findViewById(R.id.sign_up_username);
        //nation_join = (EditText) findViewById(R.id.sign_up_nation);

        btn = (Button) findViewById(R.id.enroll_btn);
        cancel_btn = (Button) findViewById(R.id.btn_cancel);

        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner.getSelectedItem().toString();
                StringBuffer tmpEmail = new StringBuffer(email_join.getText().toString().trim());
                tmpEmail.append("@knu.ac.kr");
                String email = tmpEmail.toString();
                Toast.makeText(SignUpActivity.this,email,Toast.LENGTH_SHORT).show();
                String pwd = pwd_join.getText().toString().trim();
                String pwdchk = pwdchk_join.getText().toString().trim();
                String username = username_join.getText().toString().trim();
                //String nation = nation_join.getText().toString().trim();
                //final Member member = new Member(email, pwd, username, nation);

                final Member member = new Member(email, pwd, username, text);

                //예외처리
                if(text.trim().equals("▼")||username.trim().equals("")||email.trim().equals("")){
//                    Toast.makeText(SignUpActivity.this,"please input blank", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SignUpActivity.this,"please fill the blank", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pwd.equals(pwdchk)) {
                    Toast.makeText(SignUpActivity.this, "check your password.", Toast.LENGTH_LONG).show();
                    return;
                }


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
                                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                                            member.setUid(firebaseAuth.getCurrentUser().getUid());
                                            passPushTokenToServer(member.getUid());



                                        }
                                    });
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Failure", Toast.LENGTH_SHORT).show();
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
    void passPushTokenToServer(String UID){
        String token = FirebaseInstanceId.getInstance().getToken();
        Map<String,Object> map = new HashMap<>();
        map.put("pushToken",token);
        //setvalue시 기준 data가 다날라감
        FirebaseDatabase.getInstance().getReference().child("Member").child(UID).updateChildren(map);
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
