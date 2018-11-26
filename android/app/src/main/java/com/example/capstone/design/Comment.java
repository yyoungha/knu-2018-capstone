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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Comment extends AppCompatActivity {

    List<CommentItem> lstComments;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //댓글이 뜨는 내용
        List<String> comments = Arrays.asList("Super!!!", "Loook Goood!", "Fantastic", "I love it");
        List<String> names = Arrays.asList("John Ivanovich", "Klara Pumpernikiel", "Camile Nowakowski", "Harry Potter");

        lstComments = new ArrayList<>();
        for(int i = 0, j = 0; i < comments.size() && j < names.size(); i++, j++) {
            lstComments.add(new CommentItem(R.drawable.picasso, comments.get(i), names.get(j), "2018.11.23"));
        }
        //


        //댓글내용쓴거를 리스트뷰에 처 넣어라
        ListView listView = (ListView)findViewById(R.id.listView_comment);
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.comment_item, lstComments);
        listView.setAdapter(commentAdapter);
        //

        //??왜썻을까 보이떽아
        Intent intent = getIntent();
        String strNick = intent.getExtras().getString("Nick");
        int resAvatar = intent.getExtras().getInt("Avatar");
        //

        //전송버튼 클릭시 테이블에 댓글 추가
        ImageView ivAdd = (ImageView) findViewById(R.id.sendCom);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText Content_selected = (EditText) findViewById(R.id.Contents);
                final String Content = Content_selected.getText().toString();

                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                String today = (new java.text.SimpleDateFormat("yyyy-MM-dd").format(date));

                Write write_comment = new Write(user.getUid(), Content, today);
               // DatabaseReference databaseReference = firebaseDatabase.getReference(Category);
                databaseReference.push().setValue(write_comment);
                Toast.makeText(Comment.this, "작성 완료", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        /*).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });*/

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
