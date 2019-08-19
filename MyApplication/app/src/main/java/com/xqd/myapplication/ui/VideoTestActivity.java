package com.xqd.myapplication.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import cn.jzvd.JZVideoPlayerStandard;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.AllCode;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/10/13.
 * 视频播放测试
 */

public class VideoTestActivity extends AppCompatActivity {

    private JZVideoPlayerStandard videoPlayer;
    private Button btFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sdk 21 以上不需要这个设置
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_test);

        videoPlayer = findViewById(R.id.video_player);
        btFull = findViewById(R.id.btn_full);

        btFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                videoPlayer.startWindowFullscreen();
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(VideoTestActivity.this,
                        R.anim.activity_fade_in, R.anim.activity_fade_out);

                ActivityCompat.startActivity(VideoTestActivity.this, new Intent(VideoTestActivity.this, SecondActivity.class), compat.toBundle());
            }
        });

        initData();
    }


    private void initData() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(0, AllCode.TEST_URL);
        videoPlayer.setUp(linkedHashMap, 0, JZVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "测试视频");

    }


}
