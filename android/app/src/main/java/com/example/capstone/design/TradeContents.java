package com.example.capstone.design;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

public class TradeContents extends AppCompatActivity { //trade내용 올라온 아이템의 내용에서 match버튼을 클릭할시 나오는 dialog match 할 것인지 아닌지 나뉨

    List<CommentItem> lstComments;
    String DATE;
    String CONTENT;
    String TITLE;
    String URL;
    String UID;
    String Obj_info;
    String Table_name;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference memRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_contents);

//        WeakHashMap<String, Member> memberWeakHashMap = Personal.getMemberWeakHashMap();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        UID=intent.getStringExtra("UID");
        DATE=intent.getStringExtra("DATE");
        CONTENT=intent.getStringExtra("CONTENT");
        TITLE=intent.getStringExtra("TITLE");
        URL=intent.getStringExtra("URL");
        Obj_info=intent.getStringExtra("Board_info");
        Table_name=intent.getStringExtra("Table_name");


        Log.i("SEX_table",Obj_info);
        Log.i("SEX_table1",Table_name);


        final ImageView item_Profile_Image = (ImageView)findViewById(R.id.Profile_image);
        final TextView item_profile_Name = (TextView)findViewById(R.id.Name);
        TextView item_Title = (TextView)findViewById(R.id.Title);
        TextView item_Content = (TextView)findViewById(R.id.Content);
        TextView item_Date = (TextView)findViewById(R.id.Date);
        final ImageView item_URL = (ImageView) findViewById(R.id.item_Image);


        item_Title.setText(TITLE);
        item_Content.setText(CONTENT);
        item_Date.setText(DATE);
        Picasso.with(TradeContents.this).load(URL).into(item_URL);

        // uid 로 멤버 찾기
        memRef = database.getInstance().getReference(); //멤버 테이블 안의 key인(UID)를 식별하겠다
        memRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("Member/"+UID).getChildren()) {
                    String value = ds.getKey();
                    if (value.equals("name")){
                        item_profile_Name.setText(ds.getValue().toString());
                    }
                    if(value.equals("imageUri")){
                        Picasso.with(TradeContents.this).load(Uri.parse(ds.getValue().toString())).into(item_Profile_Image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


//        item_profile_Name.setText(memberWeakHashMap.get(UID).getName());
//        Picasso.with(TradeContents.this).load(memberWeakHashMap.get(UID).getimageUri()).into(item_URL);
//        Picasso.with(TradeContents.this).load(memberWeakHashMap.get(UID).getimageUri()).into(item_URL);


//        List<String> comments = Arrays.asList("Super!!!", "Loook Goood!", "Fantastic", "I love it");
//        List<String> names = Arrays.asList("John Ivanovich", "Klara Pumpernikiel", "Camile Nowakowski", "Harry Potter");
//
//        lstComments = new ArrayList<>();
//        for(int i = 0, j = 0; i < comments.size() && j < names.size(); i++, j++) {
//            lstComments.add(new CommentItem(R.drawable.picasso, comments.get(i), names.get(j), "2018.11.23"));
//        }
//
//        ListView listView = (ListView)findViewById(R.id.comment_trade);
//        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comment_item, lstComments);
//        listView.setAdapter(commentAdapter);
//
//        Button btn = (Button)findViewById(R.id.match);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showMsg();
//            }
//        });
//
//        ImageView ivAdd = (ImageView) findViewById(R.id.sendCom);
//        ivAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        });

        //댓글추가
        ImageView ivAdd = (ImageView) findViewById(R.id.sendCom);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText Content_selected = (EditText) findViewById(R.id.writeCom);
                final String Content = Content_selected.getText().toString();

                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                String today = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(date));

                CommunityWrite write = new CommunityWrite(user.getUid(), Content, today);
                //DatabaseReference databaseReference = firebaseDatabase.getReference(Table_name).child(Obj_info).child("comment");
                //databaseReference.push().setValue(write);
                Toast.makeText(TradeContents.this, "작성 완료", Toast.LENGTH_LONG).show();
                //finish();

            }
        });


    }
    public void showMsg(){

        //다이얼로그 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //속성 지정
        builder.setTitle("Real-time Trading System");
        builder.setMessage("Match 하시겠습니까?");
        //아이콘
        builder.setIcon(android.R.drawable.ic_dialog_alert);


        //예 버튼 눌렀을 때
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //텍스트 뷰 객체를 넣어줌..
                //Snackbar.make(textView ,"예버튼이 눌렸습니다",Snackbar.LENGTH_SHORT).show();
            }
        });


        //예 버튼 눌렀을 때
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //텍스트 뷰 객체를 넣어줌..
                //Snackbar.make(textView ,"아니오 버튼이 눌렸습니다",Snackbar.LENGTH_SHORT).show();
            }
        });

        //만들어주기
        AlertDialog dialog = builder.create();
        dialog.show();
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