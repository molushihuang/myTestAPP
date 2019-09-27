package com.xqd.myapplication.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.xqd.myapplication.R;

/**
 * Created by 谢邱东 on 2019/9/27 11:20.
 * NO bug
 */
public class MockGpsProviderActivity extends Activity implements LocationListener {
    public static final String TAG = "MockGpsProviderActivity";

    //网上的一些文章说要使用net_work 我测试发现要修改位置只能只用gps的provider
    public static final String providerStr = LocationManager.GPS_PROVIDER;

    private TextView mTvLocation;
    private LocationManager mLocationManager;
    private MockThread mMockThread;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        mTvLocation = (TextView) findViewById(R.id.text);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        rmNetworkProvider();
        setNewNetworkProvider();
        mLocationManager.clearTestProviderLocation(providerStr);
        mLocationManager.requestLocationUpdates(providerStr, 0, 0, this);

        mMockThread = new MockThread();
        mMockThread.start();

    }

    private void rmNetworkProvider() {
        try {
            if (mLocationManager.isProviderEnabled(providerStr)) {
                Log.d(TAG, "now remove NetworkProvider");
//                locationManager.setTestProviderEnabled(providerStr,true);
                mLocationManager.removeTestProvider(providerStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "rmNetworkProvider error");
        }
    }

    private void setNewNetworkProvider() {
        try {
            mLocationManager.addTestProvider(providerStr, false, false,
                    false, false, false, false,
                    false, 1, Criteria.ACCURACY_FINE);
            Log.d(TAG, "addTestProvider[network] success");
//            locationManager.setTestProviderStatus("network", LocationProvider.AVAILABLE, null,
        } catch (SecurityException e) {
            Log.d(TAG, "setNewNetworkProvider error");
        }
        if (!mLocationManager.isProviderEnabled(providerStr)) {
            Log.d(TAG, "now  setTestProviderEnabled[network]");
            mLocationManager.setTestProviderEnabled(providerStr, true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            //移除GpsMockProvider
            mLocationManager.removeTestProvider(providerStr);
            mMockThread.interrupt();
        } catch (Exception e) {
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mTvLocation.setText("longitude:" + location.getLongitude()
                + "\nlatitude:" + location.getLatitude()
                + "\ntime:" + location.getTime()
                + "\naltitude:" + location.getAltitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


    private class MockThread extends Thread {

        @Override
        public void run() {
            while (true) {

                Location location = new Location(providerStr);
                location.setLatitude(46.67757400);
                location.setLongitude(29.630668000);
                location.setAltitude(634.61);
                location.setAccuracy(100.0f);
                location.setElapsedRealtimeNanos(System.currentTimeMillis());
                location.setTime(System.currentTimeMillis());

                // 向GpsMockProvider提供一个位置信息
                mLocationManager.setTestProviderLocation(providerStr, location);

                try {
                    Thread.sleep(200);
                    // 停止当前线程(重要！！)
                    if (Thread.currentThread().isInterrupted())
                        throw new InterruptedException("");
                } catch (InterruptedException e) {
                    break;
                }
            }

        }
    }

}
