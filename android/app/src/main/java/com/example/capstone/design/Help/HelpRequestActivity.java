package com.example.capstone.design.Help;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.design.MainActivity;
import com.example.capstone.design.R;
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

        helpRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser mFirebaseUser = MainActivity.getmFirebaseUser();
                Location location = HelpActivity.getmLastKnownLocation();
                String uid = mFirebaseUser.getUid();

                requestTitle = helpRequestTitle.getText().toString();
                requestContents = helpRequestContents.getText().toString();

                if ( requestTitle.equals("") ) {
                    Toast.makeText(HelpRequestActivity.this, "Please input title.", Toast.LENGTH_SHORT).show();
                    return;
                } else if ( requestContents.equals("") ) {
                    Toast.makeText(HelpRequestActivity.this, "Please input contents.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Help help = new Help(uid, requestTitle, requestContents, location.getLatitude(), location.getLongitude());

                databaseReference.push().setValue(help);

                Toast.makeText(HelpRequestActivity.this, "Request Success", Toast.LENGTH_LONG).show();
                finish();
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
