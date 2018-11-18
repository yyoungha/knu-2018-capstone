package com.example.capstone.design;


import android.app.Activity;
import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    /*
     * onCreate() 는 Activity 가 생성되어 처음 시작될 때 처음으로 호출되는 메소드.
     * Activity 의 resource initialize, layout & data binding 등 초기 설정 작업을 수행한다.
     * Bundle 객체를 매개변수로 받는다. 새로 시작되는 경우 Bundle 은 null.
     * onPause() 혹은 onStop() 상태에서 다시 시작하는 경우 Bundle 에 해당 Activity 의 이전 상태 정보가 전달된다.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* xml 문서를 실제 안드로이드 프로그램이 실행될 때 메모리에 올리는 과정은
         * ADT에 포함된 AAPT(Android Asset Packaging Tool) 에 의해 수행된다.
         * 바로 이러한 수행을 하도록 코드상에 존재하는 메소드가 setContentView()이다.
         * 이러한 동작을 Inflation(전개)이라고 한다.
         */
        setContentView(R.layout.activity_main);

        /* setSupportActionBar 메서드는 Toolbar 를 Activity 의 Appbar 로 설정한다. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* PagerAdapter 인터페이스를 구현하여 각 페이지를 Fragment 로 표현한다. */
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        /* ViewPager 는 사용자가 좌우로 페이지를 넘길 수 있게 해주는 Layout manager */
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /* TabLayout은 가로로 정렬된 탭을 보여주는 Layout */
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                return true;
                default:

            return super.onOptionsItemSelected(item);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    return new BlankFragment4();
                case 1:
                    return new BlankFragment1();
                case 2:
                    return new BlankFragment2();
                case 3:
                    return new BlankFragment3();
            }
            return new BlankFragment4();
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }
}