package com.example.capstone.design;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelpRequestActivity extends AppCompatActivity {

    private EditText helpRequestTitle;      // 제목
    private EditText helpRequestContents;   // 내용

    private String requestTitle;
    private String requestContents;

    private Button helpRequestBtn;
    private Button cancelRequestBtn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("Help");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helprequest);

        helpRequestTitle = (EditText)findViewById(R.id.help_request_title);
        helpRequestContents = (EditText)findViewById(R.id.help_request_contents);
        helpRequestBtn = (Button) findViewById(R.id.help_request_btn);
        cancelRequestBtn = (Button) findViewById(R.id.help_cancel_btn);

        requestTitle = helpRequestTitle.getText().toString();
        requestContents = helpRequestContents.getText().toString();

        helpRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser mFirebaseUser = MainActivity.getmFirebaseUser();
                Location location = HelpActivity.getmLastKnownLocation();
                String uid = mFirebaseUser.getUid();

                Help help = new Help(uid, requestTitle, requestContents, location.getLatitude(), location.getLongitude());

                // TODO : CREATE HASH
                databaseReference.push().setValue(help);

                Toast.makeText(HelpRequestActivity.this, "요청 성공", Toast.LENGTH_LONG).show();
            }
        });
        
        cancelRequestBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
