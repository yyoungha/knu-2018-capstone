package com.example.capstone.design;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Itemenroll extends AppCompatActivity {

    private EditText txt1,txt2; //제목 내용
    public String msg;

    private Button btn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemenroll);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txt1 = (EditText)findViewById(R.id.Title);
        msg = txt1.getText().toString();


        btn = (Button)findViewById(R.id.complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Electronics").push().setValue(msg);  //child는 JSON 데이터 형식에서 데이터의 '이름'을 의미
                databaseReference.child("Electronics").child("gbgg").setValue(msg); //message-gbgg항목의 값을 msg로 덮어 씌운다 문약 -gbgg항목이 없으면 새로 생
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
