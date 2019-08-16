package com.xqd.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.AESUtil;
import com.xqd.myapplication.util.EncryptUtil;
import com.xqd.myapplication.util.JNIUtil;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvHello;
    View ivDoufu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvHello = (TextView) findViewById(R.id.tv_hello);
        ivDoufu = (View) findViewById(R.id.iv_doufu);
        toolbar = findViewById(R.id.main_toolbar);

        //        toolbar.setTitle("444");
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
//                ActivityCompat.startActivity(MainActivity.this, new Intent(MainActivity.this, SecondActivity.class), compat.toBundle());
            }
        });

//        jniUtil.accessField();
//        tvHello.setText(jniUtil.name);

        tvHello.setText(new JNIUtil().accessConstructor() + "");

        int[] array = {10, 5, 100, 78, 45};
        new JNIUtil().giveArray(array);
        for (int i : array) {
            Log.e("数组排序", i + "");
        }
        Log.e("base64", EncryptUtil.getBase64Encode("BaiduMapSDK_map_for_bikenavi_v5_4_0"));
        Log.e("AES加密", AESUtil.getInstance().encrypt("草泥马"));
        Log.e("AES解密", AESUtil.getInstance().decrypt("OBz8V5O4uA4FBhQJFfCMgg=="));


//        Log.e("签名", EncryptUtil.md5(EncryptUtil.getSignature(this)));
//        Log.e("c签名", EncryptUtil.md5(JNIUtil.getSignature(MainActivity.this)));


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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);


    }
}
