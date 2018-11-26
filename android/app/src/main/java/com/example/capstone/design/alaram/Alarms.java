package com.example.capstone.design.alaram;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.capstone.design.Listener.ChatListener;
import com.example.capstone.design.R;

import java.util.ArrayList;

public class Alarms extends Fragment { //거래 부분
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Alarms() {
    }

    public static Alarms newInstance(String param1, String param2) {
        Alarms fragment = new Alarms();
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
        View view = inflater.inflate(R.layout.fragment_alarms, container, false);


        Button btn_chat = (Button)view.findViewById(R.id.btn_chat);


        // 데이터 1000개 생성--------------------------------.
        String[] strDate = {"2017-01-03", "1965-02-23", "2016-04-13", "2010-01-01", "2017-06-20",
                "2012-07-08", "1980-04-14", "2016-09-26", "2014-10-11", "2010-12-24"};
        int nDatCnt=0;
        ArrayList<AlarmsData> oData = new ArrayList<>();
        for (int i=0; i<1000; ++i)
        {
            AlarmsData oItem = new AlarmsData();
            oItem.strTitle = "데이터 " + (i+1);
            oItem.strDate = strDate[nDatCnt++];
            oData.add(oItem);
            if (nDatCnt >= strDate.length) nDatCnt = 0;
        }

        // ListView, Adapter 생성 및 연결 ------------------------
        ListView m_oListView = (ListView) view.findViewById(R.id.alarm_List);
        AlarmsAdapter oAdapter = new AlarmsAdapter(oData);
        m_oListView.setAdapter(oAdapter);


        //채팅 부분
        btn_chat.setOnClickListener(new ChatListener(getActivity()));

        //Inflate the layout for this fragment
        return view;
    }

}
