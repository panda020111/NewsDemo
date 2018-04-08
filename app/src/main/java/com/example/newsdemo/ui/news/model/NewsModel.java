package com.example.newsdemo.ui.news.model;

import android.util.Log;

import com.example.newsdemo.constant.TypeUrl;
import com.example.newsdemo.contract.NewsContract;
import com.example.newsdemo.entity.NewsGson;
import com.example.newsdemo.retrofit.ApiService;
import com.example.newsdemo.retrofit.RetrofitWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yunchang on 2018/4/8.
 */

public class NewsModel {

    private static final String TAG = "NewsModel";

    private String appKey = "4dfbc459493c09b5ffb3ec97250e5a0b";

    public void loadData(int type, final NewsContract.onLoadDataListener listener, int page) {
        ApiService apiManager = RetrofitWrapper.getInstance().create(ApiService.class);
        apiManager.getNewsData(TypeUrl.getTypeUrl(type), appKey, "10", page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<NewsGson, List<NewsGson.NewsBean>>() {
                    @Override
                    public List<NewsGson.NewsBean> call(NewsGson newsGson) {
                        List<NewsGson.NewsBean> newsList = new ArrayList<>();
                        for (NewsGson.NewsBean newsBean : newsGson.getNewslist()) {
                            NewsGson.NewsBean news = new NewsGson.NewsBean();
                            news.setTitle(newsBean.getTitle());
                            news.setCtime(newsBean.getCtime());
                            news.setDescription(newsBean.getDescription());
                            news.setPicUrl(newsBean.getPicUrl());
                            news.setUrl(newsBean.getUrl());
                            newsList.add(news);
                        }
                        return newsList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsGson.NewsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                        listener.onFailure("请求失败", e);
                    }

                    @Override
                    public void onNext(List<NewsGson.NewsBean> newsBeans) {
                        listener.onSuccess(newsBeans);
                    }
                });
    }


}
