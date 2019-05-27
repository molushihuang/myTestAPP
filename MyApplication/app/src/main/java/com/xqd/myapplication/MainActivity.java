package com.xqd.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

        tvHello.setText(new Java2CJNI().getName()+new Java2CJNI().getPassword());

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

        startActivity(new Intent(this, ContraintActivity.class));
    }


}