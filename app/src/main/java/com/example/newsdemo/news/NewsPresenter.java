package com.example.newsdemo.news;

import android.content.Context;
import android.util.Log;

import com.example.newsdemo.data.entity.NewsGson;
import com.example.newsdemo.data.NewsModel;

import java.util.List;

/**
 * Created by yunchang on 2018/4/8.
 */

public class NewsPresenter implements NewsContract.Presenter, NewsContract.onLoadDataListener{

    private static final String TAG = "NewsPresenter";

    private NewsContract.View mView;
    private NewsModel mNewsModel;
    private Context mContext;

    public NewsPresenter(NewsContract.View view, Context context) {
        mView = view;
        mContext = context;
        mNewsModel = new NewsModel();
    }

    @Override
    public void loadData(int type, int page) {
        mNewsModel.loadData(type, this, page);
    }


    @Override
    public void onSuccess(List<NewsGson.NewsBean> list) {
        mView.returnData(list);
    }

    @Override
    public void onFailure(String str, Throwable e) {
        Log.d(TAG, "onFailure: on request json data ");
    }
}
