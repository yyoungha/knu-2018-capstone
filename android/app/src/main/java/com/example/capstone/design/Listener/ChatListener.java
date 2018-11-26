package com.example.capstone.design.Listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.capstone.design.Member;
import com.example.capstone.design.message.MessageActivity;

public class ChatListener  implements View.OnClickListener {
    private Context context;
    private String destinationUID;
    public ChatListener(Context context,String destinationUID){
        this.context = context;
        this.destinationUID = destinationUID;
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra("destinationUID",destinationUID);
        context.startActivity(intent);
    }
}
