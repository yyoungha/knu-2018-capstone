package com.example.capstone.design;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Market extends AppCompatActivity { //전자 욕실 등 6가지 item이 있고 클릭시 해당하는 카테고리로 넘어감.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton btn1 = (ImageButton) findViewById(R.id.Electronic);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                intent.putExtra("name","Electronics");
                startActivity(intent);
            }
        });

        ImageButton btn2 = (ImageButton) findViewById(R.id.Bathroom);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Bathroom");
                startActivity(intent);
            }
        });

        ImageButton btn3 = (ImageButton) findViewById(R.id.Kitchen);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Kitchen");
                startActivity(intent);
            }
        });

        ImageButton btn4 = (ImageButton) findViewById(R.id.Office);

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Office");
                startActivity(intent);
            }
        });

        ImageButton btn5 = (ImageButton) findViewById(R.id.Interior);

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Interior");
                startActivity(intent);
            }
        });

        ImageButton btn6 = (ImageButton) findViewById(R.id.Daily_supplies);

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Daily");
                startActivity(intent);
            }
        });

        Button posted = (Button)findViewById(R.id.posted);
        posted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Market.this,"There are 8", Toast.LENGTH_LONG).show();
            }
        });

        Button market_complete = (Button)findViewById(R.id.market_complete);
        market_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Market.this,"No complete trade", Toast.LENGTH_LONG).show();

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


