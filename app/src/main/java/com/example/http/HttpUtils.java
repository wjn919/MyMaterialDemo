package com.example.http;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by wjn on 2016/11/3.
 */

public class HttpUtils {
    private static Retrofit mRetrofit;
    private static volatile HttpUtils httpUtils;
    public HttpUtils(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(ClientFactory.INSTANCE.getHttpClient()).build();




    }

     public static HttpUtils getInstance(String baseUrl) {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils(baseUrl);
                    return httpUtils;
                }
            }
        }
        return httpUtils;
    }

    public <T> T create(Class<T> cls) {
        return mRetrofit.create(cls);
    }

}
