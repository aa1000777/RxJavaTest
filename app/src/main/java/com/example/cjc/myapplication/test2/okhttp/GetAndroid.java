package com.example.cjc.myapplication.test2.okhttp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by cjc on 17/9/16.
 */
public interface GetAndroid {
    @GET("/api/data/Android{dataTime}")
    Observable<Android> getAndroid(@Path("dataTime")String dataTime);
}
