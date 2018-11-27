package com.example.capstone.design.message;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.capstone.design.Member;
import com.example.capstone.design.Personal;
import com.example.capstone.design.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity { //현재 저장된 메시지를 뿌려주는 곳

    private String destinationUid;
    private Button button;
    private EditText editText;

    private String uid;
    private String chatRoomUid;

    private RecyclerView recyclerView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private Member destinationMember;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    int peopleCount = 0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid(); //단말기에 로그인된 uid
        destinationUid = getIntent().getStringExtra("destinationUID"); //채팅을 당하는 아이디
        button = (Button)findViewById(R.id.messageActivity_button);
        editText = (EditText)findViewById(R.id.messageActivity_editText);
        recyclerView = (RecyclerView)findViewById(R.id.messageActivity_recyclerview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChatModel chatModel = new ChatModel();
                chatModel.users.put(uid,true);
                chatModel.users.put(destinationUid,true);

                if(chatRoomUid ==null){
                    button.setEnabled(false);
                    //push()를 넣어줘야 임의적으로 이름이 생기는 채팅방이 만들어짐
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                        }
                    });
                }else{
                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = editText.getText().toString();
                    comment.timestamp = ServerValue.TIMESTAMP;
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue("comments").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            sendFCM();
                            editText.setText("");
                        }
                    });
                }
            }
        });


    }
    void sendFCM(){
        final String SERVER_KEY="AAAAn5AfB_U:APA91bFxf8kjSHimRR0memCb2N_AKij5ma59y7HTs9UQF4YhA-6fMkYz4EW5kqTBLIYLtxdPtghkgTmIktqduMLelF864uUIvrh7KeDD73Vq21tk-R1FgsiS1IasHp67wY4zgLC0uvpe";
        final String ADDRESS="https://fcm.googleapis.com/fcm/send";
        Gson gson = new Gson();


        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = destinationMember.getToken();
        notificationModel.notification.title = Personal.getName();
        notificationModel.notification.text = editText.getText().toString();
        notificationModel.data.title = Personal.getName();
        notificationModel.data.text = editText.getText().toString();


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"),gson.toJson(notificationModel));
        Request request = new Request.Builder().header("Content-Type","application/json").addHeader("Authorization",
                "key="+SERVER_KEY).url(ADDRESS).post(requestBody).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });



    }
    void checkChatRoom(){
        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    ChatModel chatModel = item.getValue(ChatModel.class);
                    if(chatModel.users.containsKey(destinationUid)){
                        chatRoomUid = item.getKey();
                        button.setEnabled(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<ChatModel.Comment> comments;
        public RecyclerViewAdapter() {
            comments = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("Member").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    destinationMember =dataSnapshot.getValue(Member.class);
                    getMessageList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        void getMessageList(){
            databaseReference=FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments");
            valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //데이터가 추가될 때 마다 서버에서 데이터를 다 쏴줌, 따라서 클리어해주지 않을 시 데이터가 쌓인다.
                    comments.clear();
                    Map<String,Object> readUsersMap = new HashMap<>();
                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        String key = item.getKey();
                        ChatModel.Comment comment_origin = item.getValue(ChatModel.Comment.class);
                        ChatModel.Comment comment_modify = item.getValue(ChatModel.Comment.class);
                        comment_modify.readUsers.put(uid,true);

                        readUsersMap.put(key,comment_modify);
                        comments.add(comment_origin);
                    }
                    if(!comments.get(comments.size()-1).readUsers.containsKey(uid)){

                        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").updateChildren(readUsersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                notifyDataSetChanged();
                                recyclerView.scrollToPosition(comments.size()-1); //맨 마지막으로 이동
                            }
                        });

                    }else{
                        notifyDataSetChanged();
                        recyclerView.scrollToPosition(comments.size()-1);
                    }


                    //메시지 갱신



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
            MessageViewHolder messageViewHolder = ((MessageViewHolder)viewHolder);
            //말풍선 나누기
            //내가보낸 메시지
            if(comments.get(position).uid.equals(uid)){//uid=내 uid
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.out_msg);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE); //내 이미지는 안보여도 되니까 감추기
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);
                setReadCounter(position,messageViewHolder.textView_readCounter_left);
            }else{ //상대가 보낸 메시지
                Glide.with(viewHolder.itemView.getContext()).load(destinationMember.getimageUri()).apply(new RequestOptions().circleCrop()).into(messageViewHolder.imageView_profile);
                messageViewHolder.textView_name.setText(destinationMember.getName());
                messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.in_msg);
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.LEFT);
                setReadCounter(position,messageViewHolder.textView_readCounter_right);
            }
            long unixTime = (long)comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            messageViewHolder.textView_timestamp.setText(time);

            ((MessageViewHolder)viewHolder).textView_message.setText(comments.get(position).message);
        }

        void setReadCounter(final int position, final TextView textView){
            if(peopleCount==0){
                FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String,Boolean> users = (Map<String,Boolean>)dataSnapshot.getValue();
                        peopleCount = users.size();
                        int count = peopleCount - comments.get(position).readUsers.size();
                        if(count >0){
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(String.valueOf(count));
                        }else{
                            textView.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else{
                int count = peopleCount - comments.get(position).readUsers.size();
                if(count >0){
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(String.valueOf(count));
                }else{
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        }
        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_name;
            public TextView textView_message;
            public ImageView imageView_profile;
            public LinearLayout linearLayout_destination;
            public LinearLayout linearLayout_main;
            public TextView textView_timestamp;
            public TextView textView_readCounter_left;
            public TextView textView_readCounter_right;
            public MessageViewHolder(View view) {
                super(view);
                textView_message =(TextView)view.findViewById(R.id.messageItem_textView_message);
                textView_name = (TextView)view.findViewById(R.id.messageItem_textview_name);
                imageView_profile = (ImageView)view.findViewById(R.id.messageItem_imageview_profile);
                linearLayout_destination = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_destination);
                linearLayout_main = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_main);
                textView_timestamp =(TextView)view.findViewById(R.id.messageItem_textView_timestamp);
                textView_readCounter_left = (TextView)view.findViewById(R.id.messageItem_textView_readCounter_left);
                textView_readCounter_right = (TextView)view.findViewById(R.id.messageItem_textView_readCounter_right);
            }
        }


    }
    @Override
    public void onBackPressed(){
        databaseReference.removeEventListener(valueEventListener);
        finish();
    }

}
