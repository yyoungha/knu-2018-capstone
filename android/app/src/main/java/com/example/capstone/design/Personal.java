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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //새로 추가한 값들
    private TextView txt_name;
    private TextView txt_nation;
    private ImageView profile;
    private String UserID;
    FirebaseAuth firebaseAuth;
    private String my_name;
    private String my_nation;
    private TextView recent_notice;
    //



    public Personal() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Personal.
     */
    // TODO: Rename and change types and number of parameters
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

        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String UID = user.getUid();

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

        //이미지 받기
        final FirebaseStorage storage = FirebaseStorage.getInstance(); //DB안의 storage를 인스턴스화 하겠다.
        //child를 구별하기 위해 넣어둔 파일 정보를 가져온다.
        final Uri Image_uri = user.getPhotoUrl(); //db안 의 storage의 url주소를 저장하겠다.
        final Task<Uri> fucking_Uri;
        StorageReference storageRef = storage.getReferenceFromUrl("gs://knu-2018-capstone.appspot.com/");
        storageRef.child(Image_uri.toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(Personal.this.getContext()).load(uri.toString()).into(image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        });



        //내가 작성한 market 요소 확인
        TextView myscript = (TextView) view.findViewById(R.id.my_script_num);
        myscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyScript.class);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(getActivity(), Help.class);
                startActivity(intent);
            }
        });

        //메시지 보기
        Button btn_msg = (Button) view.findViewById(R.id.btn_msgbox);
        btn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Message.class);
                startActivity(intent);
            }
        });



        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(); //db를 인스턴스화 하겠다
        DatabaseReference noteRef = database.getReference("Notification"); //테이블이름 참조하겠다
        final DatabaseReference memRef = database.getReference("Member/"+UID); //멤버 테이블 안의 key인(UID)를 식별하겠다


        recent_notice = (TextView) view.findViewById(text_contentOfNotice);
        txt_name = (TextView) view.findViewById(R.id.name);
        txt_nation = (TextView) view.findViewById(R.id.nation);

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
        memRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    String target = chidSnap.getKey();
                    if(target.equals("name")){
                        my_name = String.valueOf(chidSnap.getValue());
                    }else if(target.equals("nation")){
                        my_nation = String.valueOf(chidSnap.getValue());
                    }
                    txt_name.setText(my_name);
                    txt_nation.setText(my_nation);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //Inflate the layout for this fragment
        return view;

    } //onCreateView 끝

}
