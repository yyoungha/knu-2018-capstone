package com.example.capstone.design;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * OnMapReadyCallback 은 map 이 사용가능할 때의 callback interface.
 * 이 interface 의 instance 가 MapFragment 혹은 MapView 에 설정되면,
 * 1) map 이 사용할 준비가 되어있고
 * 2) GoogleMap instance 가 non-null 일 때
 * onMapReady(GoogleMap) 메소드가 호출된다.
 * An activity that displays a map showing the place at the device's current location.
 */
public class HelpActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private Button addRequestButton;

    private static final String TAG = HelpActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Kyungpook Natl. Univ., Korea) and
    // default zoom to use when location permission is not granted.
    private final LatLng mDefaultLocation = new LatLng(35.886903, 128.608485);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located.
    // That is, the last-known location retrieved by the Fused Location Provider.
    private static Location mLastKnownLocation;

    public static Location getmLastKnownLocation() { return mLastKnownLocation; }

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private DatabaseReference mDatabase;
    private WeakHashMap<String, Help> helpWeakHashMap = new WeakHashMap<>();
    private final int MATCH_REQUEST = 1000;

    /**
     * onCreate() 는 Activity 가 생성되어 처음 시작될 때 처음으로 호출되는 메소드.
     * Activity 의 resource initialize, layout & data binding 등 초기 설정 작업을 수행한다.
     * Bundle 객체를 매개변수로 받는다. 새로 시작되는 경우 Bundle 은 null.
     * onPause() 혹은 onStop() 상태에서 다시 시작하는 경우 Bundle 에 해당 Activity 의 이전 상태 정보가 전달된다.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bundle 로부터 location, camera position 정보를 불러온다.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // xml 문서를 실제 안드로이드 프로그램이 실행될 때 메모리에 올리는 과정은
        // ADT에 포함된 AAPT(Android Asset Packaging Tool) 에 의해 수행된다.
        // 바로 이러한 수행을 하도록 코드상에 존재하는 메소드가 setContentView()이다.
        // 이러한 동작을 Inflation(전개)이라고 한다.
        setContentView(R.layout.activity_help);

        // Google Play services SDK 11부터 바뀐 위치 호출 방법.
        // 구글에서는 Google Play services 11.6.0 혹은 그 이상 권장 (bug fix 포함)
        // 1. FusedLocationProviderClient 객체를 얻어와서
        // 2. getLastLocation 메소드 호출
        // 3. completion listener 추가
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addRequestButton = (Button) findViewById(R.id.add_request_btn);

        addRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HelpActivity.this, HelpRequestActivity.class);
                startActivity(intent);
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    // Firebase RDB 로부터 HELP 데이터 불러오기

    /**
     * 시스템은 사용자가 Activity 를 떠날 경우 onSaveInstanceState() 를 호출하여 Bundle 객체에 저장.
     * Bundle 은 Activity instance 재생성 시 onCreate() 와 onRestoreInstanceState() 메서드 모두에 전달.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( mMap != null ) {
            mMap.clear();
            setMarkersOnMap();
        }
    }

    /**
     * map 이 사용가능하면 호출되는 callback method. class 상단 주석 참조.
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        /**
         * Provides views for customized rendering of info windows.
         * setInfoWindow, getInfoContents 메소드는 Marker 의 정보 창을 표시할 때 차례로 호출된다.
         * 함수 호출 원인에 상관없이 호출된다. (사용자 제스쳐 혹은 showInfoWindow() 호출과 같은)
         */

        // 사용자 위치 권한을 획득한다. (Prompt the user for permission.)
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent popUpIntent = new Intent(HelpActivity.this, HelpMatchPopup.class);

                // WeakHashMap 에서 Marker와 일치하는 객체 찾기. Hash 값 비교를 통해 찾는다.
                Help hp = helpWeakHashMap.get( marker.getSnippet() );


                // marker와 일치하는 help instance 의 정보 표시
                if ( hp != null )
                {
                    popUpIntent.putExtra("username", hp.getName());
                    popUpIntent.putExtra("title", hp.getTitle());
                    popUpIntent.putExtra("contents", hp.getContents());
                    popUpIntent.putExtra("uid", hp.getUid());
                }

                startActivityForResult(popUpIntent, MATCH_REQUEST);

                return false;
            }
        });

        getDeviceLocation();
        setCustomLayout();
        setMarkersOnMap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MATCH_REQUEST) {
            if (resultCode == RESULT_OK) {
                // 
            }
        }

    }

    private void setCustomLayout() {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(new MapStyleOptions(getResources()
                    .getString(R.string.blue_map)));
            if (!success) {
                Log.e(null, "Style parsing failed.");
            }
            Log.d(null, "success");
        } catch (Resources.NotFoundException e) {
            Log.e(null, "Can't find style. Error: ", e);
        }
    }

    private void setMarkersOnMap() {
        helpWeakHashMap.clear();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot ds : dataSnapshot.child("Help").getChildren() ) {
                    Help help = ds.getValue(Help.class);
                    helpWeakHashMap.put(ds.getKey(), help);

                    // create MarkerOption
                    MarkerOptions options = new MarkerOptions().position(new LatLng(help.getLat(), help.getLng())).title(help.getTitle());
                    options.snippet(ds.getKey());

                    // add marker to map
                    mMap.addMarker(options);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * 장치의 위치정보를 가져올 수 있도록 location 권한을 요청한다.
         * 권한 요청의 결과는 onRequestPermissionResult callback 에 의해 처리된다.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * 위치 권한 요청의 결과를 처리한다.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Prompts the user to select the current place from a list of likely places,
     * and shows the current place on the map - provided the user has granted location permission.
     */
    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
