package com.example.capstone.design;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message extends AppCompatActivity { //현재 저장된 메시지를 뿌려주는 곳
    private Toolbar toolbar;
    List<MessageItem> lstMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> comments = Arrays.asList("Super!!!", "Loook Goood!", "Fantastic", "I love it");
        List<String> names = Arrays.asList("John Ivanovich", "Klara Pumpernikiel", "Camile Nowakowski", "Harry Potter");

        lstMessage = new ArrayList<>();
        for(int i = 0, j = 0; i < comments.size() && j < names.size(); i++, j++) {
            lstMessage.add(new MessageItem(R.drawable.picasso, comments.get(i), names.get(j), "2018.11.23"));
        }

        ListView listView = (ListView)findViewById(R.id.listview_message);
        MessageAdapter messageAdapter = new MessageAdapter(this, R.layout.message_item, lstMessage);
        listView.setAdapter(messageAdapter);

        /*Intent intent = getIntent();
        String strNick = intent.getExtras().getString("Nick");
        int resAvatar = intent.getExtras().getInt("Avatar");
*/

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
