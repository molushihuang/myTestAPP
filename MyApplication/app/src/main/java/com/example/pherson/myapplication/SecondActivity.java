package com.example.pherson.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SecondActivity extends AppCompatActivity {

    ImageView ivDoufu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sdk 21 以上不需要这个设置
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        ivDoufu = (ImageView) findViewById(R.id.iv_doufu);
        ivDoufu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

    }

}
