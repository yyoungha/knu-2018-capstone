package com.example.capstone.design;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private TextView recent_notice;

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

        //내 글 확인

        TextView myscript = (TextView) view.findViewById(R.id.my_script_num);
        myscript.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyScript.class);
                startActivity(intent);
            }
        });

        //전체 공지

        TextView notice = (TextView) view.findViewById(R.id.notice_all);
        notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),NoticeActivity.class);
                startActivity(intent);
            }
        });

        //마켓

        Button btn_market = (Button) view.findViewById(R.id.btn_market);
        btn_market.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Market.class);
                startActivity(intent);
            }
        });

        //전체 세팅

        Button btn_help = (Button) view.findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Help.class);
                startActivity(intent);
            }
        });

        //메시지
        Button btn_msg = (Button) view.findViewById(R.id.btn_msgbox);
        btn_msg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Message.class);
                startActivity(intent);
            }
        });


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference noteRef = database.getReference("Notification");

        recent_notice = (TextView)view.findViewById(text_contentOfNotice);


        noteRef.orderByChild("Date").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Notification notification = dataSnapshot.getValue(Notification.class);
                Log.v("Content : ", notification.getContent());
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
    }

}
