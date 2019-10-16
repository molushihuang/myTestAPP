package com.xqd.myapplication.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.xqd.myapplication.R;

public class AsyncTaskActivity extends AppCompatActivity {

    private Button btStart;
    private Button btCancel;
    private ProgressBar pbDownload;
    private MyTask mMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        btStart = findViewById(R.id.bt_start);
        btCancel = findViewById(R.id.bt_cancel);
        pbDownload = findViewById(R.id.pb_download);

        mMyTask = new MyTask();
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyTask.execute("sfd");
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyTask.cancel(true);
            }
        });
    }


    private class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btStart.setText("正在下载");
        }

        @Override
        protected String doInBackground(String... strings) {

            int count = 0;
            while (count < 100) {

                count = count + 1;
                publishProgress(count);
                SystemClock.sleep(1000);

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pbDownload.setProgress(values[0]);
            btStart.setText("下载进度 "+values[0]+"%");
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            btStart.setText("下载完成");
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            btStart.setText("取消");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


    }

    @Override
    protected void onDestroy() {
        if (mMyTask != null)
            mMyTask.cancel(true);
        super.onDestroy();
    }
}
