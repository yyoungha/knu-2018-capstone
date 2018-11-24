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
    private Toolbar toolbar;
    private ImageButton btn_ELECTRONICS;
    private ImageButton btn_BATHROOM;
    private ImageButton btn_KITCHEN;
    private ImageButton btn_OFFICE;
    private ImageButton btn_INTERIOR;
    private ImageButton btn_DAILYSUPPLIES;
    private Button btn_MARKETPOSTED;
    private Button btn_COMPLETE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        this.initializeValues();
        this.addListener();
    }

    private void addListener() {
        btn_ELECTRONICS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                intent.putExtra("name","Electronics");
                startActivity(intent);
            }
        });
        btn_BATHROOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Bathroom");
                startActivity(intent);
            }
        });
        btn_KITCHEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Kitchen");
                startActivity(intent);
            }
        });
        btn_OFFICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Office");
                startActivity(intent);
            }
        });
        btn_INTERIOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Interior");
                startActivity(intent);
            }
        });
        btn_DAILYSUPPLIES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this,listActivity.class);
                //db electronic 전달
                intent.putExtra("name","Daily");
                startActivity(intent);
            }
        });
        btn_MARKETPOSTED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Market.this,"There are 8", Toast.LENGTH_LONG).show();
            }
        });
        btn_COMPLETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Market.this,"No complete trade", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void initializeValues() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_ELECTRONICS = (ImageButton) findViewById(R.id.Electronic);
        btn_BATHROOM = (ImageButton) findViewById(R.id.Bathroom);
        btn_KITCHEN = (ImageButton) findViewById(R.id.Kitchen);
        btn_OFFICE = (ImageButton) findViewById(R.id.Office);
        btn_INTERIOR = (ImageButton) findViewById(R.id.Interior);
        btn_DAILYSUPPLIES = (ImageButton) findViewById(R.id.Daily_supplies);
        btn_MARKETPOSTED = (Button)findViewById(R.id.posted);
        btn_COMPLETE = (Button)findViewById(R.id.market_complete);

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


