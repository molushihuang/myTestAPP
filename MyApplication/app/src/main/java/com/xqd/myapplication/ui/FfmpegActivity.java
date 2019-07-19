package com.xqd.myapplication.ui;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.FFmpegBridge;

public class FfmpegActivity extends AppCompatActivity {

    private ProgressBar pbVideo;
    private Button bnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg);
        pbVideo=findViewById(R.id.pb_video);
        bnStart=findViewById(R.id.bn_start);
        bnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void start(){
        pbVideo.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String basePath = Environment.getExternalStorageDirectory().getPath();

                String cmd_transcoding = String.format("ffmpeg -i %s -c:v libx264 %s  -c:a libfdk_aac %s",
                        basePath+"/"+"girl.mp4",
                        "-crf 40",
                        basePath+"/"+"my_girl.mp4");
                int i = jxFFmpegCMDRun(cmd_transcoding);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        pbVideo.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
    public  int jxFFmpegCMDRun(String cmd){
        String regulation="[ \\t]+";
        final String[] split = cmd.split(regulation);

        return FFmpegBridge.jxCMDRun(split);
    }

}
