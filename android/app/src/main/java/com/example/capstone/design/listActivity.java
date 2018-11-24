package com.example.capstone.design;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listActivity extends AppCompatActivity { //전자 or 욕실 등 클릭 했을때 DB정보를 뿌려주는 listview(=recylerview) 커스텀 list를 만들었음

    SearchView mSearchView;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    //db테이블 이름 값 저장 변수
    String table_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바 기능 구현
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼 ,디폴트로 true만 해도 백버튼이 생김


        //db 테이블 이름 저장
        Intent intent = getIntent(); /*데이터 수신*/
        String table_name = intent.getExtras().getString("name"); /*String형*/

        setTitle(table_name);

        /*final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,LIST_ITEM);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);*/

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemInfo> itemInfoArrayList = new ArrayList<>();
        itemInfoArrayList.add(new ItemInfo(R.drawable.mouse,"마우스 팔아요~","2018-11-23"));  //여기 하드코딩으로 추가만 해주면 들어감 여기를 이제 db랑 연결 시키면 끝~
        itemInfoArrayList.add(new ItemInfo(R.drawable.daumlogo,"마우스 팔아요~","2018-11-23"));
        itemInfoArrayList.add(new ItemInfo(R.drawable.mouse,"마우스 팔아요~","2018-11-23"));

        MyAdapter myAdapter = new MyAdapter(itemInfoArrayList);
        mRecyclerView.setAdapter(myAdapter);

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
