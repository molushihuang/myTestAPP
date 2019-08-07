package com.xqd.myapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.AESUtil;
import com.xqd.myapplication.util.EncryptUtil;
import com.xqd.myapplication.util.JNIUtil;

public class MainActivity extends AppCompatActivity {
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

        tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext();
            }
        });
        ivDoufu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNext();
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
        Log.e("AES加密",AESUtil.getInstance().encrypt("草泥马"));
        Log.e("AES解密",AESUtil.getInstance().decrypt("OBz8V5O4uA4FBhQJFfCMgg=="));

        new Thread(new Runnable() {

            @Override
            public void run() {
                for(int i=0;i<100;i++){
                    JNIUtil.startClient(i+"","192.168.3.93",9998);
                }
            }
        }).start();

//        Log.e("签名", EncryptUtil.md5(EncryptUtil.getSignature(this)));
//        Log.e("c签名", EncryptUtil.md5(JNIUtil.getSignature(MainActivity.this)));

//        AnimatorSet objectAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
//        objectAnimator.setTarget(tvHello);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();

//        tvHello.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("translationY", 0, 500);
//                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(tvHello, propertyValuesHolder);
//                objectAnimator.setDuration(1200);
//                objectAnimator.setInterpolator(new BounceInterpolator());
//                objectAnimator.start();
//            }
//        },1000);


//        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
    }

    private void toNext() {

//        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(ivDoufu,
//                        ivDoufu.getWidth() / 2, ivDoufu.getHeight() / 2, 0, 0);

//        Pair<View, String> imagePair = Pair.create(ivDoufu, getString(R.string.transition_name));
//        Pair<View, String> textPair = Pair.create(tvHello, getString(R.string.transition_name2));
//
//        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imagePair,textPair);
//
//        ActivityCompat.startActivity(MainActivity.this, new Intent(MainActivity.this, SecondActivity.class), compat.toBundle());

//        startActivity(new Intent(this, VoiceActivity.class));
    }


}
