package com.example.capstone.design;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class HelpMatchPopup extends Activity {

    TextView nameTextView;
    TextView titleTextView;
    TextView contentsTextView;
    Intent intent;

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

        //데이터 가져오기
        intent = getIntent();
        String username = intent.getStringExtra("username");
        String title = intent.getStringExtra("title");
        String contents = intent.getStringExtra("contents");

        nameTextView.setText(username);
        titleTextView.setText(title);
        contentsTextView.setText(contents);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

}