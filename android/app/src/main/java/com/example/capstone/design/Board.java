package com.example.capstone.design;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Board#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Board extends Fragment { //게시판 자유게시판 or 정보 등
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //상빈이가 추가한 값
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    List<CommunityWrite> lstCommunity;
    private DatabaseReference mDatabase;
    String[] array = new String[5];
    private BoardAdapter boardAdapter;
    ArrayList<CommunityWrite> itemInfoArrayList = new ArrayList<>();
    //


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //List of items
    //List<CommunityWrite> lstCommunity;


    public Board() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Board.
     */
    // TODO: Rename and change types and number of parameters
    public static Board newInstance(String param1, String param2) {
        Board fragment = new Board();
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
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview_id);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 카테고리에 맞게 각각 입력된 값 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //table_name에 해당하는 값만 for-each문으로 불러온다.
                for( DataSnapshot ds : dataSnapshot.child("Board").getChildren() ) {
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
                        }if(key.equals("title")){
                            array[2]=td.get(key);
                            communityWrite.setTitle(array[2]);
                        }if(key.equals("content")){
                            array[3]=td.get(key);
                            communityWrite.setContent(array[3]);
                        }if(key.equals("url")){
                            array[4]=td.get(key);
                            communityWrite.setUrl(array[4]);
                        }
                    }
                    itemInfoArrayList.add(communityWrite);
                    boardAdapter = new BoardAdapter(itemInfoArrayList);
                    mRecyclerView.setAdapter(boardAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*lstCommunity = new ArrayList<>();
        for(int i = 0; i <20; i++) {
            lstCommunity.add(new CommunityWrite(R.drawable.picasso, R.drawable.picasso, "Picasso was a great artist and very talented one. His great works were famous all over the world", "Pablo Picasso", "2018-11-20"));
        }*/

        /*RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstCommunity);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(recyclerViewAdapter);*/

        FloatingActionButton add_board = (FloatingActionButton) view.findViewById(R.id.add_board);
        add_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //등록
                Intent intent = new Intent(getActivity(),Boardenroll.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
