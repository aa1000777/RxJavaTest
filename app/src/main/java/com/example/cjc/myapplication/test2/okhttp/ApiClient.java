package com.example.cjc.myapplication.test2.okhttp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    public static GetAndroid weatherService;
    public static void init() {
        weatherService = initWeatherService(ApiConstants.API_HOST, GetAndroid.class);
    }

    private static <T> T initWeatherService(String baseUrl, Class<T> clazz) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(clazz);
    }

}

