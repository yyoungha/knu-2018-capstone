package com.example.yunsangbin.a03_tabapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetailedInfoMenu extends AppCompatActivity {


    List<DataItem> lstData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info_menu);

        //TODO: change to JSON file pull data

        ImageView ivPhoto = (ImageView)findViewById(R.id.ivIconDet);
        TextView tvDescription = (TextView)findViewById(R.id.tvDescriptionDet);
        TextView tvName = (TextView)findViewById(R.id.tvNameDet);
        TextView tvKakaoId = (TextView)findViewById(R.id.tvKakaoDet);

        tvDescription.setText("Picasso was a great artist");
        tvName.setText("Pablo Picasso");
        ivPhoto.setImageResource(R.drawable.picasso);
        tvKakaoId.setText("picasso123");
    }
}
