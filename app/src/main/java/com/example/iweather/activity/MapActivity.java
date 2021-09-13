package com.example.iweather.activity;

        import android.Manifest;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.NavigationView;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.content.ContextCompat;
        import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.baidu.location.BDAbstractLocationListener;
        import com.example.iweather.R;
        import com.baidu.location.BDLocation;
        import com.baidu.location.BDLocationListener;
        import com.baidu.location.LocationClient;
        import com.baidu.location.LocationClientOption;
        import com.baidu.mapapi.SDKInitializer;
        import com.baidu.mapapi.map.BaiduMap;
        import com.baidu.mapapi.map.MapStatusUpdate;
        import com.baidu.mapapi.map.MapStatusUpdateFactory;
        import com.baidu.mapapi.map.MapView;
        import com.baidu.mapapi.map.MyLocationData;
        import com.baidu.mapapi.model.LatLng;
        import com.example.iweather.adpter.MyFragmentPagerAdapter;
        import com.example.iweather.fragment.MainFragment;
        import com.example.iweather.fragment.MultiCityFragment;
        import com.example.iweather.util.MySharedpreference;

        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.List;


/*
   定位
 */
public class MapActivity extends AppCompatActivity {

    public LocationClient mLocationClient;
    private Button button1;
    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;



    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapview);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.
                permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this, permissions, 1);
        } else {
            requestLocation();
        }
     /*   button1 =(Button)findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                Intent intent=new Intent(MapActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors); //低精度
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy); // 高精度
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

  private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    public class MyLocationListener implements BDLocationListener {
        public String city;

        private SharedPreferences.Editor  editor;       //共享参数

        public void onReceiveLocation(final BDLocation Location) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {


                    if (Location.getLocType() == BDLocation.TypeGpsLocation || Location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        navigateTo(Location);
                        editor = MySharedpreference.getInstance(MapActivity.this);
                        city = Location.getCity();
                        editor.putString("City", city);
                        editor.commit();
                    }

                    button1 =(Button)findViewById(R.id.button);
                    button1.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void  onClick(View v){
                            editor = MySharedpreference.getInstance(MapActivity.this);
                            city = Location.getCity();
                            editor.putString("City", city);
                            editor.commit();
                            Intent intent=new Intent(MapActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    });
                        }
                    });
                }
            }
        }







