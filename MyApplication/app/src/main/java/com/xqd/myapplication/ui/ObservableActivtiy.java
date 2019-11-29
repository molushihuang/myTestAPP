package com.xqd.myapplication.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.xqd.myapplication.R;
import com.xqd.myapplication.bean.NewsBean;
import com.xqd.myapplication.bean.ResponsBase;
import com.xqd.myapplication.http.APIServiceImp;
import io.reactivex.functions.Consumer;

import java.util.List;


/**
 * Created by 谢邱东 on 2019/11/27 14:08.
 * Rxjava测试类
 * NO bug
 */
public class ObservableActivtiy extends AppCompatActivity {
    private final String TAG = ObservableActivtiy.class.getSimpleName();
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
        initView();
    }

    private void initView() {
        mButton = findViewById(R.id.bt_start);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startObservable();
            }
        });
    }

    private void startObservable() {
//        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("hello");
//                e.onNext("1");
//                e.onComplete();
//
//            }
//        });
//
//        Consumer consumer = new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, s);
//            }
//        };
//        Consumer throwableConsumer = new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable o) throws Exception {
//                Log.e(TAG, o.toString());
//            }
//        };
//        Action action = new Action() {
//            @Override
//            public void run() throws Exception {
//                Log.e(TAG, "onComplete");
//            }
//        };
//        observable.subscribe(consumer, throwableConsumer, action);


//        Observable observable = Observable.just("hello", "1", "2");
//        Observable.fromArray(1, 2, 3)
//                //指定被观察者线程（新线程）
//                .subscribeOn(Schedulers.newThread())
//                //指定观察者线程（主线程）
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer s) throws Exception {
//                        //map里面做转换
//                        return "index " + s;
//                    }
//                })
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        //必须在主线程执行，doOnSubscribe可以指定线程
//                        mButton.setBackgroundColor(Color.YELLOW);
//                        Log.e(TAG, "doOnSubscribe "+disposable.isDisposed());
//                    }
//                })
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.e(TAG, "onSubscribe "+d.isDisposed());
//                    }
//
//                    @Override
//                    public void onNext(String o) {
//                        Log.e(TAG, o);
//                        mButton.setText(o);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.e(TAG, "onComplete");
//                    }
//                });


//        APIServiceImp.getInstance().getGirls(1, 10, new Observer<ResponsBase<NewsBean>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(ResponsBase<NewsBean> responsBase) {
//                List<NewsBean> newsBeans = responsBase.getResults();
//                mButton.setText(newsBeans.get(0).getCreatedAt());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        APIServiceImp.getInstance().getGirls(1, 10,"福利", new Consumer<ResponsBase<NewsBean>>() {
            @Override
            public void accept(ResponsBase<NewsBean> newsBeanResponsBase) throws Exception {
                List<NewsBean> newsBeans = newsBeanResponsBase.getResults();
                mButton.setText(newsBeans.get(0).getCreatedAt());
            }
        });
    }
}
