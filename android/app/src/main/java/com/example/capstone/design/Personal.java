package com.example.capstone.design;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Intent.getIntent;
import static android.support.constraint.Constraints.TAG;
import static com.example.capstone.design.R.id.name;
import static com.example.capstone.design.R.id.text_contentOfNotice;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Personal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Personal extends Fragment { //main화면 창 각 버튼 클릭시 화면으로 넘어감
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView tv_NAME;
    private TextView tv_NATION;
    private ImageView profile;
    private String UserID;
    private FirebaseAuth firebaseAuth;
    private static String my_name;
    private String my_nation;
    private TextView tv_CURRENTNOTIFICATION;
    private String UID;
    private FirebaseUser USER;
    private Uri uri_IMAGE;
    private ImageView img_PROFILE;
    private FirebaseStorage fb_STORAGE;
    private StorageReference ref_STORAGE;
    private TextView tv_NOTICEALL;
    private Button btn_MARKET;
    private Button btn_HELP;
    private Button btn_MSGBOX;
    private FirebaseDatabase fb_DATABASE;
    private DatabaseReference ref_NOTIFICATION;
    private DatabaseReference ref_MEMBER;
    private View VIEW;
    // Required empty public constructor
    public Personal(){ }

    public static String getName() { return my_name; }
    public static Personal newInstance(String param1, String param2) {
        Personal fragment = new Personal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        VIEW = inflater.inflate(R.layout.fragment_personal, container, false);

        this.initializeValues();
        this.addListener();

        return VIEW;

    } //onCreateView 끝


    private void addListener() {
        img_PROFILE.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        tv_NOTICEALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        btn_MARKET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Market.class);
                startActivity(intent);
            }
        });

        btn_HELP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        btn_MSGBOX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Message.class);
                startActivity(intent);
            }
        });

        ref_NOTIFICATION.orderByChild("Date").addChildEventListener(new ChildEventListener() { //공지를 날짜순으로 정렬 후 리스터 생성
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { //snapshot(table전체와 같음)
                Notification notification = dataSnapshot.getValue(Notification.class);
                tv_CURRENTNOTIFICATION.setText(notification.getContent());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //member reference : firebase instance
        ref_MEMBER.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    String target = chidSnap.getKey();
                    if(target.equals("name"))
                        tv_NAME.setText(String.valueOf(chidSnap.getValue()));
                    else if(target.equals("nation"))
                        tv_NATION.setText(String.valueOf(chidSnap.getValue()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initializeValues() {
        USER = FirebaseAuth.getInstance().getCurrentUser();
        UID = USER.getUid();
        img_PROFILE = (ImageView)VIEW.findViewById(R.id.profile);
        fb_STORAGE = FirebaseStorage.getInstance(); //DB안의 storage를 인스턴스화 하겠다.
        uri_IMAGE = USER.getPhotoUrl(); //db안 의 storage의 url주소를 저장하겠다.
        ref_STORAGE = fb_STORAGE.getReferenceFromUrl("gs://knu-2018-capstone.appspot.com/");
        if ( uri_IMAGE == null ) {
            // 저장된 이미지 없음. 기본 이미지 설정
        } else {
            ref_STORAGE.child(uri_IMAGE.toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(Personal.this.getContext()).load(uri.toString()).into(img_PROFILE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }
        tv_NOTICEALL = (TextView) VIEW.findViewById(R.id.notice_all);
        btn_MARKET = (Button) VIEW.findViewById(R.id.btn_market);
        btn_HELP = (Button) VIEW.findViewById(R.id.btn_help);
        btn_MSGBOX = (Button) VIEW.findViewById(R.id.btn_msgbox);
        fb_DATABASE = FirebaseDatabase.getInstance(); //db를 인스턴스화 하겠다
        ref_NOTIFICATION = fb_DATABASE.getReference("Notification"); //테이블이름 참조하겠다
        ref_MEMBER = fb_DATABASE.getReference("Member/"+UID); //멤버 테이블 안의 key인(UID)를 식별하겠다
        tv_CURRENTNOTIFICATION = (TextView) VIEW.findViewById(text_contentOfNotice);
        tv_NAME = (TextView) VIEW.findViewById(R.id.name);
        tv_NATION = (TextView) VIEW.findViewById(R.id.nation);
    }
}
