package com.example.capstone.design;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

public class Comment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<CommentItem> lstComments;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String UID;
    String Obj_info;
    private DatabaseReference mDatabase;
    private CommentAdapter commentAdapter;
    String[] array = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        UID=intent.getStringExtra("UID");
        Obj_info=intent.getStringExtra("Board_info");

        TextView item_profile_Name = (TextView)findViewById(R.id.Name);
        ImageView item_URL = (ImageView) findViewById(R.id.item_Image);

        // uid 로 멤버 찾기
        /*item_profile_Name.setText(memberWeakHashMap.get(UID).getName());
        Picasso.with(Comment.this).load(memberWeakHashMap.get(UID).getimageUri()).into(item_URL);*/

        // 카테고리에 맞게 각각 입력된 값 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //table_name에 해당하는 값만 for-each문으로 불러온다.
                for( DataSnapshot ds : dataSnapshot.child("Board").child(Obj_info).child("comment").getChildren() ) {
                    CommunityWrite communityWrite = new CommunityWrite();
                    HashMap<String,String> td = (HashMap)(ds.getValue());
                    Iterator<String> keys = td.keySet().iterator();
                    //모든 게시판 내용 값 불러오기
                    while( keys.hasNext() ){
                        String key = keys.next(); //key값 순차적으로 찍힐 거임
                        if(key.equals("date")){
                            array[0]=td.get(key);
                            communityWrite.setDate(array[0]);
                        }if(key.equals("uid")){
                            array[1]=td.get(key);
                            communityWrite.setUid(array[1]);
                        }if(key.equals("content")){
                            array[2]=td.get(key);
                            communityWrite.setContent(array[2]);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //댓글이 뜨는 내용
        List<String> comments = Arrays.asList("Super!!!", "Loook Goood!", "Fantastic", "I love it");
        List<String> names = Arrays.asList("John Ivanovich", "Klara Pumpernikiel", "Camile Nowakowski", "Harry Potter");

        lstComments = new ArrayList<>();
        for(int i = 0, j = 0; i < comments.size() && j < names.size(); i++, j++) {
            lstComments.add(new CommentItem(R.drawable.picasso, comments.get(i), names.get(j), "2018.11.23"));
        }
        //


        //댓글내용쓴거를 어댑터 연결해서 리스트뷰에 처 넣어라
        ListView listView = (ListView)findViewById(R.id.listView_comment);
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comment_item, lstComments);
        listView.setAdapter(commentAdapter);
        //


        //전송버튼 클릭시 테이블에 댓글 추가
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
                DatabaseReference databaseReference = firebaseDatabase.getReference("Board").child(Obj_info).child("comment");
                databaseReference.push().setValue(write);
                Toast.makeText(Comment.this, "작성 완료", Toast.LENGTH_LONG).show();
                finish();

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (resultCode == RESULT_OK) {
            switch(requestCode) {
                case 3000:
                    String image = data.getExtras().getString("result");
                    if(image!= null) {
                        TextView argu1 = (TextView)findViewById(R.id.path);
                        argu1.setText(data.getStringExtra("result"));
                        Image_uri= Uri.parse(data.getStringExtra("result"));
                    }
                    break;
            }
        }*/

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
