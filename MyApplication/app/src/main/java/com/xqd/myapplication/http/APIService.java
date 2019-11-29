package com.xqd.myapplication.http;

import com.xqd.myapplication.bean.NewsBean;
import com.xqd.myapplication.bean.ResponsBase;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by 谢邱东 on 2019/11/28 13:39.
 * NO bug
 */
public interface APIService {

    /**
     * 分类资源
     * @param url
     * @return
     */
    @GET()
    Observable<ResponsBase<NewsBean>>getGirls(@Url String url);
}
