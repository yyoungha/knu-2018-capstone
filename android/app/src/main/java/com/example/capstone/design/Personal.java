package com.example.capstone.design;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.design.tool.CropCircle;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;

import static android.content.Intent.getIntent;
import static android.support.constraint.Constraints.TAG;
import static com.example.capstone.design.R.id.name;
import static com.example.capstone.design.R.id.text_contentOfNotice;

public class Personal extends Fragment { //main화면 창 각 버튼 클릭시 화면으로 넘어감

    //새로 추가한 값들
    private TextView tv_NAME;
    private TextView tv_NATION;
    TextView script;
    private static String username;
    private static String useremail;
    private TextView recent_notice;
    private String UID;
    private int total_count=0;
    //

    private boolean isMemberLoaded = false;
    private static WeakHashMap<String, Member> memberWeakHashMap = new WeakHashMap<>();

    // Required empty public constructor
    public Personal(){ }

    public static String getName() { return username; }

    public static String getEmail() { return useremail; }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        script = (TextView)view.findViewById(R.id.my_script_num);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UID = user.getUid();

        //이미지 추가하기 Imageactivity로 넘어감
        final ImageView image = (ImageView)view.findViewById(R.id.profile);

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(); //db를 인스턴스화 하겠다
        DatabaseReference countRef = database.getReference();
        DatabaseReference noteRef = database.getReference("Notification"); //테이블이름 참조하겠다
        final DatabaseReference memRef = database.getReference("Member/"+UID); //멤버 테이블 안의 key인(UID)를 식별하겠다
        final DatabaseReference memAllRef = database.getReference("Member");


        if (!isMemberLoaded) memberWeakHashMap.clear();
        memAllRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!isMemberLoaded) {
                    for (DataSnapshot ds : dataSnapshot.child("Member").getChildren() ) {
                        Member member = ds.getValue(Member.class);
                        memberWeakHashMap.put(ds.getKey(), member);
                    }
                    isMemberLoaded = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //member reference : firebase instance
        memRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    String target = chidSnap.getKey();
                    if (target.equals("name")) {
                        tv_NAME.setText(String.valueOf(chidSnap.getValue()));
                        username = tv_NAME.getText().toString();
                    } else if (target.equals("nation")) {
                        tv_NATION.setText(String.valueOf(chidSnap.getValue()));
                    }
                    else if (target.equals("email")) {
                        // email 받아오기
                        useremail = String.valueOf(chidSnap.getValue());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        //이미지 받기
        final FirebaseStorage storage = FirebaseStorage.getInstance(); //DB안의 storage를 인스턴스화 하겠다.
        //child를 구별하기 위해 넣어둔 파일 정보를 가져온다.
        final Uri Image_uri = user.getPhotoUrl(); //db안 의 storage의 url주소를 저장하겠다.
        StorageReference storageRef = storage.getReferenceFromUrl("gs://knu-2018-capstone.appspot.com/");

        if ( Image_uri == null ) {
            // 저장된 이미지 없음. 기본 이미지 설정
        } else {
            storageRef.child(Image_uri.toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(Personal.this.getContext()).load(uri.toString()).transform(new CropCircle()).into(image);

                    Picasso.with(Personal.this.getContext()).load(uri.toString()).into(image);
                    if ( memberWeakHashMap.isEmpty() )
                        Log.i("SEX3 empty hash map.",UID);
                    else
                        memberWeakHashMap.get(UID).setimageUri(uri.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        }

        // 자신이 작성한 글 개수
        countRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String value = ds.getKey();
                    if (value.equals("Electronics")||value.equals("Board")||value.equals("Daily")||value.equals("Office")||value.equals("Kitchen")||value.equals("Bathroom")||value.equals("Interior")) {
                        String target = ds.getValue().toString();
                            for (DataSnapshot ds2 : dataSnapshot.child(value).getChildren()) {
                                String value2 = ds2.getKey();
                                HashMap<String,String> td = (HashMap)(ds2.getValue());
                                Iterator<String> keys = td.keySet().iterator();
                                while( keys.hasNext() ) {
                                    String key = keys.next(); //key값 순차적으로 찍힐 거임
                                    if (key.equals("uid")) {
                                        String power = td.get(key);
                                        if(power.equals(UID))
                                            total_count++;
                                    }
                                }
                            }
                        }
                    }
                script.setText(String.valueOf(total_count));
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            } //공지를 날짜순으로 정렬 후 리스터 생성
        });



        // 자신이 작성한 글 보러가기
//        script.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MyScript.class);
//                startActivity(intent);
//            }
//        });


        //전체 공지 보기

        TextView notice = (TextView) view.findViewById(R.id.notice_all);
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
            }
        });

        //마켓 들어가기

        Button btn_market = (Button) view.findViewById(R.id.btn_market);
        btn_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Market.class);
                startActivity(intent);
            }
        });

        //help map 들어가기

        Button btn_help = (Button) view.findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });

        //메시지 보기
        Button btn_msg = (Button) view.findViewById(R.id.btn_msgbox);
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });





        recent_notice = (TextView) view.findViewById(text_contentOfNotice);
        tv_NAME = (TextView) view.findViewById(R.id.name);
        tv_NATION = (TextView) view.findViewById(R.id.nation);

        noteRef.orderByChild("Date").addChildEventListener(new ChildEventListener() { //공지를 날짜순으로 정렬 후 리스터 생성
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { //snapshot(table전체와 같음)
                Notification notification = dataSnapshot.getValue(Notification.class);
                recent_notice.setText(notification.getContent());
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

        //Inflate the layout for this fragment
        return view;

    } //onCreateView 끝

    public static WeakHashMap<String, Member> getMemberWeakHashMap() { return memberWeakHashMap; }
}
