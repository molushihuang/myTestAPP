package com.xqd.myapplication.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.xqd.myapplication.R;
import com.xqd.myapplication.util.DecelerateAccelerateInterpolator;
import com.xqd.myapplication.util.DisplayUtil;

/**
 * Created by 谢邱东 on 2019/8/16 09:27.
 * NO bug
 */
public class AnimatorActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ImageView ivPoint;
    private ImageView ivRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ivPoint = findViewById(R.id.iv_point);
        ivRing = findViewById(R.id.iv_ring);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar); //将toolbar设置为actionbar

        pointAnimation();
    }

    //黄色球的动画
    private void pointAnimation() {

        //调用xml的方式
//        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
//        animation.setInterpolator(new LinearInterpolator());
//        ivPoint.startAnimation(animation);

        //java代码实现
//        RotateAnimation rotateAnimation3 = new RotateAnimation(0, 359,
//                Animation.ABSOLUTE, DisplayUtil.dip2px(this, 7), Animation.ABSOLUTE, DisplayUtil.dip2px(this, 78));
//        rotateAnimation3.setFillAfter(true);
//        rotateAnimation3.setStartOffset(1000);
//        rotateAnimation3.setDuration(2000);
//        rotateAnimation3.setRepeatCount(-1);
//        rotateAnimation3.setInterpolator(new DecelerateAccelerateInterpolator());
//        rotateAnimation3.setRepeatMode(Animation.RESTART);
//        ivPoint.startAnimation(rotateAnimation3);

        //属性动画
        ivPoint.setPivotX(DisplayUtil.dip2px(this, 7));
        ivPoint.setPivotY(DisplayUtil.dip2px(this, 78));
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("rotation", 0.0f, 359f);
        ObjectAnimator rotate = ObjectAnimator.ofPropertyValuesHolder(ivPoint, propertyValuesHolder);
//        ObjectAnimator rotate = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.rotate_animator);//从xml文件加载
//        rotate.setTarget(ivPoint);
        rotate.setDuration(2000);
        rotate.setStartDelay(1000);
        rotate.setInterpolator(new DecelerateAccelerateInterpolator());
        rotate.setRepeatCount(-1);
        rotate.start();

        //ValueAnimator 动画
//        ivPoint.setPivotX(DisplayUtil.dip2px(this, 7));//设置指定旋转中心点X坐标
//        ivPoint.setPivotY(DisplayUtil.dip2px(this, 78));
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 359);
//        animator.setTarget(ivPoint);
//        animator.setDuration(2000);
//        animator.setRepeatCount(-1);
//        animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                ivPoint.setRotation((Float) animation.getAnimatedValue());
//            }
//        });
//        animator.start();


//        AnimatorSet objectAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
//        objectAnimator.setTarget(ivRing);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();

//        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("translationY", 0, 500);
//        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(ivRing, propertyValuesHolder2);
//        objectAnimator.setDuration(1200);
//        objectAnimator.setRepeatCount(-1);
//        objectAnimator.setRepeatMode(ObjectAnimator.REVERSE);
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();

    }

}
