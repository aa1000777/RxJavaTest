package com.example.cjc.myapplication.test2.okhttp;

import android.content.Context;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by aa1000777 on 2017/3/22.
 * aa1000777.github.io
 */

public class ApiDataRepository {
    public static Observable<Android> getAndroid(Context context, String dataTime) {
        return ApiClient.weatherService.getAndroid(dataTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
