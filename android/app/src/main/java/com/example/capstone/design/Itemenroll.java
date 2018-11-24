package com.example.capstone.design;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import android.widget.TextView;

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

public class Itemenroll extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText txt1,txt2; //제목 내용
    public String msg;

    private Button btn;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemenroll);

        //스피너
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        //어댑터 생성
        //이 예제 같은 경우 string,xml에 리스트를 추가해 놓고 그 리스트를 불러온다.
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.table_name, android.R.layout.simple_spinner_item);
        //스피너와 어댑터 연결
        spinner.setAdapter(adapter);
        //이벤트를 일으킨 위젯과 리스너와 연결
        spinner.setOnItemSelectedListener(this);
        //선택된 값 스트링으로 받기
        final String text = spinner.getSelectedItem().toString();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt1 = (EditText) findViewById(R.id.Title);
        msg = txt1.getText().toString();


        btn = (Button) findViewById(R.id.complete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Electronics").push().setValue(msg);  //child는 JSON 데이터 형식에서 데이터의 '이름'을 의미
                databaseReference.child("Electronics").child("gbgg").setValue(msg); //message-gbgg항목의 값을 msg로 덮어 씌운다 문약 -gbgg항목이 없으면 새로 생
            }
        });
        final Button image_button = (Button) findViewById(R.id.bt_choose);

        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Itemenroll.this, MarketImageActivity.class);
                startActivity(intent);
            }
        });

        //이미지 받기
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseStorage storage = FirebaseStorage.getInstance(); //DB안의 storage를 인스턴스화 하겠다.
        //child를 구별하기 위해 넣어둔 파일 정보를 가져온다.
        final Uri Image_uri = user.getPhotoUrl(); //db안 의 storage의 url주소를 저장하겠다.
        StorageReference storageRef = storage.getReferenceFromUrl("gs://knu-2018-capstone.appspot.com/market");

        if ( Image_uri == null ) {
            // 저장된 이미지 없음. 기본 이미지 설정
        } else {
            storageRef.child(Image_uri.toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    //Picasso.with(Personal.this.getContext()).load(uri.toString()).into(image);
                    TextView path = (TextView)findViewById(R.id.path);
                    path.setText(uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
