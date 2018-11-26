package com.example.capstone.design;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //List of items
    List<CommunityItem> lstCommunity;


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

        lstCommunity = new ArrayList<>();
        for(int i = 0; i <20; i++) {
            lstCommunity.add(new CommunityItem(R.drawable.picasso, R.drawable.picasso, "Picasso was a great artist and very talented one. His great works were famous all over the world", "Pablo Picasso", "2018-11-20"));
        }

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstCommunity);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(recyclerViewAdapter);

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
