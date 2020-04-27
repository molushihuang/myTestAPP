package com.xqd.myapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.xqd.myapplication.R

/**
 * Created by 谢邱东 on 2020/4/27 16:59.
 *kotlin 测试类
 * NO bug
 */
class KotlinTestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        var tvName: TextView = findViewById(R.id.tv_name);
        tvName?.setOnClickListener {  Toast.makeText(this, "sdfsd", Toast.LENGTH_LONG).show()}

//        tv_name?.text = "东爷"
//        tv_name?.setOnClickListener(View.OnClickListener { Toast.makeText(this,"sdfsd", Toast.LENGTH_LONG).show() });
//        tv_name.setOnClickListener { Toast.makeText(this, "sdfsd", Toast.LENGTH_LONG).show() }
    }
}