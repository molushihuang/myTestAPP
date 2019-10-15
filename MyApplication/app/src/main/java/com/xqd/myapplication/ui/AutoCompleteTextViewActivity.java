package com.xqd.myapplication.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.xqd.myapplication.R;

public class AutoCompleteTextViewActivity extends AppCompatActivity {

    private AutoCompleteTextView atName;
    private String[] data = {"小猪猪", "小傻瓜", "小狗狗", "小笨蛋", "猪八戒"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete_text_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        atName = findViewById(R.id.at_name);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_auto, data);
        atName.setAdapter(arrayAdapter);


    }

}
