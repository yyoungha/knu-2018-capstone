package com.example.capstone.design.Listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.capstone.design.Member;
import com.example.capstone.design.message.MessageActivity;

public class ChatListener  implements View.OnClickListener {
    private Member member;
    private Context context;
    private String destinationUID;
    public ChatListener(Context context,String destinationUID){}
    public ChatListener(Context context){
        this.context = context;
        this.destinationUID = destinationUID;
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context,MessageActivity.class);
        intent.putExtra("destinationUID",member.getUid());
        context.startActivity(intent);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
