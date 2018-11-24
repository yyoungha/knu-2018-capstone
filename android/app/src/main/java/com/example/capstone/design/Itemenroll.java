package com.example.capstone.design;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Itemenroll extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Uri Image_uri;
    private Toolbar toolbar;
    private String image_hash;
    private String path;
    private String text;
    private Spinner spinner;
    private Button image_button;
    private Button upload_button;

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemenroll);

        this.initializeValues();
        this.addListener();

    }

    private void addListener() {
        //이미지 등록을 위한 activity를 띄워준다.
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Itemenroll.this, MarketImageActivity.class);
                startActivityForResult(intent,3000);
            }
        });


        /*
        LAYOUT 각각의 값을 가져와서, Write Class로 넣고
        database에 Input으로 넣어줍니다.

        Image값은 image_hash를 넣어준다.
        data는 function call을 통해 현재 시간을 넣어준다.
        */
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 받기
                final FirebaseStorage storage = FirebaseStorage.getInstance(); //DB안의 storage를 인스턴스화 하겠다.
                //child를 구별하기 위해 넣어둔 파일 정보를 가져온다.
                StorageReference storageRef = storage.getReferenceFromUrl("gs://knu-2018-capstone.appspot.com/");

                if (Image_uri == null) {
                    // 저장된 이미지 없음. 기본 이미지 설정
                } else {
                    storageRef.child(Image_uri.toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            path = uri.toString(); // path에 다운로드 이미지 링크를 넣어준다.

                            final String Category = spinner.getSelectedItem().toString();
                            TextView fuck = (TextView)findViewById(R.id.path);

                            EditText Title_selected = (EditText) findViewById(R.id.Title);
                            final String Title = Title_selected.getText().toString();
                            EditText Content_selected = (EditText) findViewById(R.id.Contents);
                            final String Content = Content_selected.getText().toString();
                            Calendar cal = Calendar.getInstance();
                            Date date = cal.getTime();
                            String today = (new SimpleDateFormat("yyyy-MM-dd").format(date));


                            Write write = new Write(user.getUid(), Title, Content, today,path);
                            DatabaseReference databaseReference = firebaseDatabase.getReference(Category);
                            databaseReference.push().setValue(write);
                            Toast.makeText(Itemenroll.this, "요청 성공", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                        }
                    });
                }

            }
        });
    }

    private void initializeValues() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //스피너
        spinner = (Spinner) findViewById(R.id.spinner);
        //어댑터 생성
        //이 예제 같은 경우 string,xml에 리스트를 추가해 놓고 그 리스트를 불러온다.
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.table_name, android.R.layout.simple_spinner_item);
        //스피너와 어댑터 연결
        spinner.setAdapter(adapter);
        //이벤트를 일으킨 위젯과 리스너와 연결
        spinner.setOnItemSelectedListener(this);
        //선택된 값 스트링으로 받기
        text = spinner.getSelectedItem().toString();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image_button = (Button) findViewById(R.id.bt_choose);
        upload_button = (Button)findViewById(R.id.complete);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
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

        if (resultCode == RESULT_OK) {
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
        }



    }
}
