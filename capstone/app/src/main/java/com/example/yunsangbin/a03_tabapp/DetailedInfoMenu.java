package com.example.yunsangbin.a03_tabapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DetailedInfoMenu extends AppCompatActivity {


    List<DataItem> lstData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info_menu);

        //TODO: change to JSON file pull data


        lstData =new ArrayList<>();
        lstData.add(new DataItem(R.drawable.picasso, "Picasso was a great artist", "Pablo Picasso", "picasso123"));
        CustomAdapter adapter = new CustomAdapter(this, R.layout.activity_detailed_info_menu, lstData);


    }

}
