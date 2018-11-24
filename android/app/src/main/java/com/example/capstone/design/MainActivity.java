package com.example.capstone.design;


import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.SigningInfo;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.navdrawer.SimpleSideDrawer;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.capstone.design.R.id.logout_btn;
import static com.example.capstone.design.R.id.text_contentOfNotice;

public class MainActivity extends AppCompatActivity{
    SimpleSideDrawer slide_menu;
    private TextView sm_email;
    Button btn_slide_menu;
    private static final String TAG = "MainActivity";
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    // Firebase instance variables
    // 1. Auth with Google Firebase
    private static FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private String mPhotoUrl;

    private static FirebaseUser mFirebaseUser;

    public static FirebaseAuth getmFirebaseAuth() { return mFirebaseAuth; }
    public static FirebaseUser getmFirebaseUser() { return mFirebaseUser; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.addfirebaseMessagingService();
        this.addSlideMenu();
        this.initializeFirebaseAuth();
        this.setActivityLayout();

    }

    private void setActivityLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //겟스트링으로 받아 온다.
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    //여기서 부터 이전 탭 레이아웃
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
}

    private void initializeFirebaseAuth() {
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Log.d(TAG, "token = " + FirebaseInstanceId.getInstance().getToken());
        }
    }

    private void addSlideMenu() {
        slide_menu = new SimpleSideDrawer(this);
        slide_menu.setLeftBehindContentView(R.layout.activity_menu);
        Button btn_slide_menu = (Button) findViewById(R.id.btn_slide_menu);

        //get a email
        sm_email = (TextView)findViewById(R.id.tv_sm_email);
        //go to international affair web
        Button btn_international = (Button) findViewById(R.id.btn_international);
        btn_international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gp.knu.ac.kr/"));
                startActivity(web);
            }
        });
        //go to our git
        Button btn_git = (Button) findViewById(R.id.btn_git);
        btn_git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/zwei2/knu-2018-capstone"));
                startActivity(web);
            }
        });
        //go to our web
        Button btn_mainweb = (Button) findViewById(R.id.btn_mainweb);
        btn_mainweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("http:iamwooki.gonetis.com:8080"));
                startActivity(web);
            }
        });

        //setting 버튼 누르면 슬라이드 메뉴로 세팅 화면 보여줌
        btn_slide_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                slide_menu.toggleLeftDrawer();    // 왼쪽에서 화면이 나오게 함

            }
        });

        //프로필 관리
        Button btn = (Button) findViewById(R.id.profile_admin);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileAdmin.class);
                startActivity(intent);
            }
        });

        //로그아웃
        Button btn_logout = (Button) findViewById(R.id.logout_btn);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addfirebaseMessagingService(){

        //target OREO or higher
        //reference : http://shnoble.tistory.com/81
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            /*개인 사용자 용*/
            String channel_id = getString(R.string.channel_id); //채널 아이디
            CharSequence channel_name = getString(R.string.channel_name); //사용자가 볼 수 있는 채널 이름
            String description = getString(R.string.description); //사용자가 볼 수 있는 구독 이름

            //기본 중요도 3 (어디서든 표시되고 알람이 울리지만 시각적인 방해는 되지 않음
            // IMPORTANCE_HIGH 높음


            /*그룹 사용자 용  -> 추후에 그룹을 만들게 되면 사용할 수 있다.*/
            String group_id = getString(R.string.group_id); //사용자가 볼 수 있는 그룹 아이디
            CharSequence group_name = getString(R.string.group_name); //사용자가 볼 수 있는 그룹 이름
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(group_id, group_name)); //그룹 등록


            //중요도로 알림 채널 생성
            NotificationChannel mChannel = new NotificationChannel(channel_id,channel_name, NotificationManager.IMPORTANCE_HIGH);
            //알림채널 설정
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //알림매니저에 알림채널 등록
            notificationManager.createNotificationChannel(mChannel);

        }//target oreo or higher end
        //TOPIC/NOTIFICATION 구독
        FirebaseMessaging.getInstance().subscribeToTopic("NOTIFICATION");
        //구독해제 unsubscribeFromTopic()
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

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
                    return new Personal(); //맨처음 화면 키자 마자 뜨는 화면
                case 1:
                    return new Alarms(); //오른쪽으로 드래그 했을때 뜨는 화면 알람
                case 2:
                    return new Board(); //게시판


            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }



}
