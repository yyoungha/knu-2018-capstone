package com.example.capstone.design;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Comment extends AppCompatActivity {

    List<CommentItem> lstComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> comments = Arrays.asList("Super!!!", "Loook Goood!", "Fantastic", "I love it");
        List<String> names = Arrays.asList("John Ivanovich", "Klara Pumpernikiel", "Camile Nowakowski", "Harry Potter");

        lstComments = new ArrayList<>();
        for(int i = 0, j = 0; i < comments.size() && j < names.size(); i++, j++) {
            lstComments.add(new CommentItem(R.drawable.picasso, comments.get(i), names.get(j), "2018.11.23"));
        }

        ListView listView = (ListView)findViewById(R.id.listView_comment);
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comment_item, lstComments);
        listView.setAdapter(commentAdapter);

        Intent intent = getIntent();
        String strNick = intent.getExtras().getString("Nick");
        int resAvatar = intent.getExtras().getInt("Avatar");

    }

}
