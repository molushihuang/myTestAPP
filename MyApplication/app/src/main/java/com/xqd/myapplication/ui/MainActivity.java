package com.xqd.myapplication.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.AESUtil;
import com.xqd.myapplication.util.EncryptUtil;
import com.xqd.myapplication.util.JNIUtil;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvHello;
    ImageView ivDoufu;
    private final int PERMISSION_REQUEST_CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvHello = (TextView) findViewById(R.id.tv_hello);
        ivDoufu = (ImageView) findViewById(R.id.iv_doufu);
        toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar); //将toolbar设置为actionbar

        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext();
            }
        });
        ivDoufu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(ivDoufu,
//                        ivDoufu.getWidth() / 2, ivDoufu.getHeight() / 2, 0, 0);
//
//                Pair<View, String> imagePair = Pair.create(ivDoufu, getString(R.string.transition_name));
//                Pair<View, String> textPair = Pair.create(tvHello, getString(R.string.transition_name2));
//
//                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imagePair, textPair);
//
//
//                ActivityCompat.startActivity(MainActivity.this, new Intent(MainActivity.this, SecondActivity.class), compat.toBundle());
            }
        });

//        jniUtil.accessField();
//        tvHello.setText(jniUtil.name);

//        tvHello.setText(new JNIUtil().accessConstructor() + "");

        int[] array = {10, 5, 100, 78, 45};
        new JNIUtil().giveArray(array);
        for (int i : array) {
            Log.e("数组排序", i + "");
        }
        Log.e("base64", EncryptUtil.getBase64Encode("BaiduMapSDK_map_for_bikenavi_v5_4_0"));
        Log.e("AES加密", AESUtil.getInstance().encrypt("草泥马"));
        Log.e("AES解密", AESUtil.getInstance().decrypt("OBz8V5O4uA4FBhQJFfCMgg=="));

//        tvHello.setText(CommomUtils.checkRootFile().getAbsolutePath());

//        Log.e("签名", EncryptUtil.md5(EncryptUtil.getSignature(this)));
//        Log.e("c签名", EncryptUtil.md5(JNIUtil.getSignature(MainActivity.this)));
        permissionCheck();
    }

    private void toNext() {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                for(int i=0;i<50;i++){
//                    JNIUtil.startClient(i+"","192.168.3.93",9998);
//                }
//            }
//        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "animator");
        menu.add(0, 2, 2, "Contraint_test");
        menu.add(0, 3, 3, "Projection_test");
        menu.add(0, 4, 4, "ActivityOptions_test");
        menu.add(0, 5, 5, "Voice_test");
        menu.add(0, 6, 6, "video_test");
        menu.add(0, 7, 7, "canvas_test");
        menu.add(0, 8, 8, "gps_test");
        menu.add(0, 9, 9, "auto_complete_textView_test");
        menu.add(0, 10, 10, "asyncTask_test");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, ContraintActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ProjectionActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, SecondActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, VoiceActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, VideoTestActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, CanvasDemoActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, MockGpsProviderActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, AutoCompleteTextViewActivity.class));
                break;
            case 10:
                startActivity(new Intent(this, AsyncTaskActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);


    }


    /**
     * 动态权限检查
     */
    private void permissionCheck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.READ_CONTACTS,
//                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.CALL_PHONE,
//                    Manifest.permission.SEND_SMS,
//                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.SET_DEBUG_APP,
//                    Manifest.permission.SYSTEM_ALERT_WINDOW,
//                    Manifest.permission.GET_ACCOUNTS,
//                    Manifest.permission.RECORD_AUDIO,
//                    Manifest.permission.CAMERA,
                    Manifest.permission.VIBRATE,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
//                    Manifest.permission.PROCESS_OUTGOING_CALLS,
//                    Manifest.permission.BODY_SENSORS,
//                    Manifest.permission.WRITE_SETTINGS,
//                    Manifest.permission.WRITE_APN_SETTINGS
            };

            boolean permissionState = true;
            for (String permission : mPermissionList) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionState = false;
                }
            }
            if (!permissionState) {
                ActivityCompat.requestPermissions(this, mPermissionList, PERMISSION_REQUEST_CODE);
            } else {
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionState = true;
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionState = false;
                    Log.e("权限错误》》》》", i + permissions[i]);
                }
            }

            if (permissionState) {
            } else {

            }
        }
    }
}
