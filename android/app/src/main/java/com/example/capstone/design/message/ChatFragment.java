package com.example.capstone.design.message;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.capstone.design.Member;
import com.example.capstone.design.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class ChatFragment extends Fragment {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.chatfragment_recyclerview);
        recyclerView.setAdapter(new ChatRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        return view;

    }
    class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<ChatModel> chatModels = new ArrayList<>();
        private String uid;
        private ArrayList<String> destinationUsers = new ArrayList<>();
        public ChatRecyclerViewAdapter() {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot item :dataSnapshot.getChildren()){
                        chatModels.clear();
                        for(DataSnapshot ITEM:dataSnapshot.getChildren()){
                            chatModels.add(item.getValue(ChatModel.class));
                        }
                        notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
            return new CustomeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
            final CustomeViewHolder customeViewHolder = (CustomeViewHolder)viewHolder;
            String destinationUid = null;
            //일일 챗방에 있는 유저 체크
            for(String user: chatModels.get(position).users.keySet()){
                if(!user.equals(uid)){
                    destinationUid = user;
                    destinationUsers.add(destinationUid);
                }
            }
            FirebaseDatabase.getInstance().getReference().child("Member").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Member member = dataSnapshot.getValue(Member.class);
                    Glide.with(customeViewHolder.itemView.getContext()).load(member.getimageUri()).apply(new RequestOptions().circleCrop()).into(customeViewHolder.imageView);
                    customeViewHolder.textView_title.setText(member.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            //메세지를 내림 차순으로 정렬 후 마지막 메시지의 키 값을 가져옴
            Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
            commentMap.putAll(chatModels.get(position).comments);
            String lastMessageKey = (String) commentMap.keySet().toArray()[0];
            customeViewHolder.textView_last_message.setText(chatModels.get(position).comments.get(lastMessageKey).message);

            customeViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),MessageActivity.class);
                    intent.putExtra("destinationUid",destinationUsers.get(position));

                    startActivity(intent);
                }
            });

            //time stamp
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            long unixTime = (long)(chatModels.get(position).comments.get(lastMessageKey).timestamp);
            Date date = new Date(unixTime);
            customeViewHolder.textView_timestamp.setText(simpleDateFormat.format(date));

        }

        @Override
        public int getItemCount() {
            return chatModels.size();
        }

        private class CustomeViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_title;
            public ImageView imageView;
            public TextView textView_last_message;
            public TextView textView_timestamp;
            public CustomeViewHolder(View view) {
                super(view);

                imageView = (ImageView)view.findViewById(R.id.chatitem_imageview);
                textView_title = (TextView)view.findViewById(R.id.chatitem_textview_title);
                textView_last_message = (TextView)view.findViewById(R.id.chatitem_textview_lastmessage);
                textView_timestamp = (TextView)view.findViewById(R.id.chatitem_textview_timestamp);
            }
        }
    }
}
