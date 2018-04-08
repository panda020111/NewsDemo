package com.example.newsdemo.retrofit;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yunchang on 2018/4/8.
 */

public class RetrofitWrapper {

    private static RetrofitWrapper instance;
    private Context mContext;
    private Retrofit mRetrofit;
    private static final String appUrl = "http://api.tianapi.com/";

    private RetrofitWrapper () {
        mRetrofit = new Retrofit.Builder().baseUrl(appUrl)
                .addConverterFactory(GsonConverterFactory.create()) // 解析方法
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static RetrofitWrapper getInstance () {
        if (instance == null) {
            synchronized (RetrofitWrapper.class) {
                if (instance == null) {
                    instance = new RetrofitWrapper();
                }
            }
        }

        return instance;
    }

    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }
}
