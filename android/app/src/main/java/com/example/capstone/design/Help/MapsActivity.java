package com.example.capstone.design.Help;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.capstone.design.Member;
import com.example.capstone.design.Personal;
import com.example.capstone.design.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// 커스텀 http://gun0912.tistory.com/57

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private CameraPosition mCameraPosition;
    private GoogleMap shareMap;
    private Button stopSharingButton;
    private final LatLng mDefaultLocation = new LatLng(35.8894983, 128.6094142);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static Location mLastKnownLocation;
    private boolean mLocationPermissionGranted = true;
    private DatabaseReference mDatabase;
    MarkerOptions marker;

    private DatabaseReference testDatabase;
    double testlat = 35.8894983;
    double testlng = 128.6094142;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_maps);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.shareMap);
        mapFragment.getMapAsync(this);

        stopSharingButton = (Button) findViewById(R.id.stop_sharing_btn);

        // 버튼 리스너 설정
        stopSharingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 실제 데이터 받아오기
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("Member").getChildren()) {
                    Member member = ds.getValue(Member.class);
                    // 사용자 위치 정보 업데이트
                    String uid = "21oTipfSBag1xpnx1yjGKNMNzPA3";
                    if ( member.getUid().equals(uid)) {
                        double lng = member.getLng();
                        double lat = member.getLat();
                        moveMarker(new LatLng(lat, lng));
                    }
                    Log.i("member id is ", member.getUid());
                    Log.i("member lat is ", ""+member.getLat());
                    Log.i("member lng is ", ""+member.getLng());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        setTestData();
    }

    private void setTestData() {
        Handler handler = new Handler();
        for(double i = 0.01; i < 0.05; i += 0.01) {
            testlat += i;
            testlng += i;
        handler.postDelayed(new Runnable() {
            public void run() {
                // 테스트데이터 쓰기
                if ( testDatabase == null )
                    testDatabase = FirebaseDatabase.getInstance().getReference("Member/"+Personal.getUid());
                    testDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Member member = dataSnapshot.getValue(Member.class);
                        // 사용자 위치 정보 업데이트

                        String uid = "21oTipfSBag1xpnx1yjGKNMNzPA3";
                        if ( member.getUid().equals(uid)) {
                            testDatabase.child("lat").setValue(testlat);
                            testDatabase.child("lng").setValue(testlng);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        }, 2000);

        }
//        testlat = 35.8894983;
//        testlng = 128.6094142;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (shareMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, shareMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        shareMap = googleMap;

        getDeviceLocation();
        updateLocationUI();
        // Add a marker in Sydney and move the camera
        LatLng knu = mDefaultLocation;
        marker = new MarkerOptions().position(knu).title("Person");
        marker.snippet("current location");
        shareMap.addMarker(marker);
        shareMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                knu, DEFAULT_ZOOM));
    }

    public void moveMarker(LatLng latLng) {
        shareMap.clear();
        marker = new MarkerOptions().position(latLng).title("Person");
        marker.snippet("Current location");
        shareMap.addMarker(marker);
        shareMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void updateLocationUI() {
        if (shareMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                shareMap.setMyLocationEnabled(true);
                shareMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                shareMap.setMyLocationEnabled(false);
                shareMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && (mLastKnownLocation = task.getResult()) != null) {
                            // Set the map's camera position to the current location of the device.
                            shareMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            shareMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            shareMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getLocationPermission() {
        /*
         * 장치의 위치정보를 가져올 수 있도록 location 권한을 요청한다.
         * 권한 요청의 결과는 onRequestPermissionResult callback 에 의해 처리된다.
         */
            mLocationPermissionGranted = true;
    }
}
