package com.example.api;

import com.example.entity.BookResponse;

import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by wjn on 2017/2/7.
 */

public interface testApi {
    @GET("https://api.douban.com/v2/book/search")
    Observable<BookResponse> getBooks(@QueryMap Map<String, String> options);
}
