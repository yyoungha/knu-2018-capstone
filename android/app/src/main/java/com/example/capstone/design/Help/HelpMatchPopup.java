package com.example.capstone.design.Help;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstone.design.R;

public class HelpMatchPopup extends Activity {

    TextView nameTextView;
    TextView titleTextView;
    TextView contentsTextView;
    Button matchButton;
    Button cancelButton;
    Intent intent;

    String username;
    String title;
    String contents;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help_match);

        //UI 객체생성
        nameTextView = (TextView)findViewById(R.id.nameTextView);
        titleTextView = (TextView)findViewById(R.id.titleTextView);
        contentsTextView = (TextView)findViewById(R.id.contentsTextView);
        matchButton = (Button)findViewById(R.id.match_cinfirm_btn);
        cancelButton = (Button)findViewById(R.id.match_cancel_btn);

        //데이터 가져오기
        intent = getIntent();
        username = intent.getStringExtra("username");
        title = intent.getStringExtra("title");
        contents = intent.getStringExtra("contents");
        uid = intent.getStringExtra("uid");

        nameTextView.setText(username);
        titleTextView.setText(title);
        contentsTextView.setText(contents);

        // MATCH 버튼 리스너 추가
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 전달하기
                Intent intent = new Intent();
                intent.putExtra("uid", uid);
                setResult(RESULT_OK, intent);

                //액티비티(팝업) 닫기
                finish();
            }
        });

        // CANCEL 버튼 리스너 추가
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}