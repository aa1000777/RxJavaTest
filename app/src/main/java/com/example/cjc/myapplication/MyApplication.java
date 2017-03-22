package com.example.cjc.myapplication;

import android.app.Application;

import com.example.cjc.myapplication.test2.okhttp.ApiClient;

/**
 * Created by aa1000777 on 2017/3/22.
 * aa1000777.github.io
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.init();
    }
}
