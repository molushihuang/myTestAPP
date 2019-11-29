package com.xqd.myapplication.http;

import com.xqd.myapplication.BuildConfig;
import com.xqd.myapplication.bean.NewsBean;
import com.xqd.myapplication.bean.ResponsBase;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by 谢邱东 on 2019/11/28 13:46.
 * NO bug
 */
public class APIServiceImp {

    public static final String API_URL = "http://gank.io/api/";
    public static final long READTIMEOUT = 60;
    public static final long CONNECTTIME_OUT = 60;
    public static final long WRITETIMEOUT = 60;

    private static APIServiceImp sAPIServiceImp = new APIServiceImp();
    private static APIService Apiservice;

    public static APIServiceImp getInstance() {
        if (sAPIServiceImp == null) {
            sAPIServiceImp = new APIServiceImp();
        }
        return sAPIServiceImp;
    }

    public APIServiceImp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        //设置头
        builder.addInterceptor(headerInterceptor);
        builder.connectTimeout(CONNECTTIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(READTIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITETIMEOUT, TimeUnit.SECONDS);
        //LOG
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        builder.addInterceptor(loggingInterceptor);

        OkHttpClient okHttpClient = builder.build();
        // 异步请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        Apiservice = retrofit.create(APIService.class);

    }

    /**
     * 分类资源
     *
     * @param pageNum
     * @param pageCount
     * @param type      数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param observer
     */
    public void getGirls(int pageNum, int pageCount, String type, Observer<ResponsBase<NewsBean>> observer) {
        Apiservice.getGirls("data/" + type + "/" + pageCount + "/" + pageNum)
                .subscribeOn(Schedulers.io())
                //指定观察者线程（主线程）
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 分类资源
     *
     * @param pageNum
     * @param pageCount
     * @param type      数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param consumer
     */
    public void getGirls(int pageNum, int pageCount, String type, Consumer<ResponsBase<NewsBean>> consumer) {
        Apiservice.getGirls("data/" + type + "/" + pageCount + "/" + pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

}
