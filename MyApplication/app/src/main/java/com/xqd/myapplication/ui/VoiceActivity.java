package com.xqd.myapplication.ui;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.JNIUtil;
import io.reactivex.functions.Consumer;
import org.fmod.FMOD;

import java.io.File;

/**
 * 变声测试页面
 */
public class VoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btNative;
    private Button btGirl;
    private Button btUncle;
    private Button btPanic;
    private Button btFunny;
    private Button btVacant;
    private JNIUtil jniUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FMOD.init(this);
        setContentView(R.layout.activity_voice);

        btNative = findViewById(R.id.bt_native);
        btGirl = findViewById(R.id.bt_girl);
        btUncle = findViewById(R.id.bt_uncle);
        btPanic = findViewById(R.id.bt_panic);
        btFunny = findViewById(R.id.bt_funny);
        btVacant = findViewById(R.id.bt_vacant);

        btNative.setOnClickListener(this);
        btGirl.setOnClickListener(this);
        btUncle.setOnClickListener(this);
        btPanic.setOnClickListener(this);
        btFunny.setOnClickListener(this);
        btVacant.setOnClickListener(this);
        jniUtil = new JNIUtil();
    }

    @Override
    public void onClick(View v) {

        if (jniUtil.isPlaying()) {
            Toast.makeText(VoiceActivity.this, "正在播放", Toast.LENGTH_SHORT).show();
            return;
        }

        mFix(v);
    }

    public void mFix(final View v) {

        RxPermissions rxPermission = new RxPermissions(VoiceActivity.this);
        rxPermission.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) {
                        if (granted) {
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "movement_over.mp3";
                            switch (v.getId()) {
                                case R.id.bt_native:
                                    jniUtil.fix(path, JNIUtil.MODE_NORMAL);
                                    break;
                                case R.id.bt_girl:
                                    jniUtil.fix(path, JNIUtil.MODE_LUOLI);
                                    break;
                                case R.id.bt_uncle:
                                    jniUtil.fix(path, JNIUtil.MODE_DASHU);
                                    break;
                                case R.id.bt_panic:
                                    jniUtil.fix(path, JNIUtil.MODE_JINGSONG);
                                    break;
                                case R.id.bt_funny:
                                    jniUtil.fix(path, JNIUtil.MODE_GAOGUAI);
                                    break;
                                case R.id.bt_vacant:
                                    jniUtil.fix(path, JNIUtil.MODE_KONGLING);
                                    break;

                                default:
                                    break;
                            }

                        } else {
                            Toast.makeText(VoiceActivity.this, "权限被拒绝,请到设置中打开", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FMOD.close();
    }


}
