package com.example.capstone.design;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class listActivity extends AppCompatActivity { //전자 or 욕실 등 클릭 했을때 DB정보를 뿌려주는 listview(=recylerview) 커스텀 list를 만들었음

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    String[] array = new String[5];
    //db테이블 이름 값 저장 변수
    String table_name;

    ArrayList<Write> itemInfoArrayList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바 기능 구현
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 ,디폴트로 true만 해도 백버튼이 생김


        //db 테이블 이름 저장
        Intent intent = getIntent(); /*데이터 수신*/
        final String table_name = intent.getExtras().getString("name"); /*String형*/

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 테마를 테이블 이름으로 설정해준다.
        setTitle(table_name);
        //Log.i("SHUTTHEFUCKUP : ",table_name);

        // 카테고리에 맞게 각각 입력된 값 가져오기
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //table_name에 해당하는 값만 for-each문으로 불러온다.
                for( DataSnapshot ds : dataSnapshot.child(table_name).getChildren() ) {
                    Write write = new Write();
                    HashMap<String,String> td = (HashMap)(ds.getValue());
                    Log.i("SEX", String.valueOf(ds.getValue()));
                    Iterator<String> keys = td.keySet().iterator();
                    Log.i("SEX", String.valueOf(td.keySet().iterator()));
                    //모든 게시판 내용 값 불러오기
                    while( keys.hasNext() ){
                        String key = keys.next(); //key값 순차적으로 찍힐 거임
                        if(key.equals("date")){
                            array[0]=td.get(key);
                            write.setDate(array[0]);
                            //Log.i("SEX",write.getDate());
                        }if(key.equals("uid")){
                            array[1]=td.get(key);
                            write.setUid(array[1]);
                            //Log.i("SEX",write.getUid());
                        }if(key.equals("title")){
                            array[2]=td.get(key);
                            write.setTitle(array[2]);
                            //Log.i("SEX",write.getTitle());
                        }if(key.equals("content")){
                            array[3]=td.get(key);
                            write.setContent(array[3]);
                           // Log.i("SEX",write.getContent());
                        }if(key.equals("url")){
                            array[4]=td.get(key);
                            write.setUrl(array[4]);
                            //Log.i("SEX",write.getUrl());
                        }
//                        String value = td.get(key); //value 값
                    }
                    itemInfoArrayList.add(write);
                    int total = itemInfoArrayList.size();
                    for(int i =0; i<total; i++)
                        Log.i("SEX_출력", String.valueOf(itemInfoArrayList.get(i)));
                    myAdapter = new MyAdapter(itemInfoArrayList);
                    mRecyclerView.setAdapter(myAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                //등록
                Intent intent = new Intent(listActivity.this,Itemenroll.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //toolbar에 있는 기능 눌렀을 때
        switch (item.getItemId()){
            case android.R.id.home:{ //home버튼이 back키임
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Search 기능
        getMenuInflater().inflate(R.menu.menu_itemlist,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);

        //글자색과 힌트 색상
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setTextColor(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //쿼리 들어가야할 부분
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //쿼리 들어가야할 부분
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}