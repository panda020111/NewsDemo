package com.example.newsdemo.data;

import android.util.Log;

import com.example.newsdemo.data.entity.PictureGson;
import com.example.newsdemo.picture.PictureContract;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.jude.easyrecyclerview.EasyRecyclerView.TAG;

/**
 * Created by yunchang on 2018/5/7.
 */

public class PictureModel {

    private String appKey = "4dfbc459493c09b5ffb3ec97250e5a0b";

    public void loadData(final PictureContract.OnLoadDataListener listener, int page) {
        ApiService apiService = RetrofitWrapper.getInstance().create(ApiService.class);
        apiService.getPictureData(appKey, "6", page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PictureGson, List<PictureGson.NewsListBean>>() {

                    @Override
                    public List<PictureGson.NewsListBean> call(PictureGson pictureGson) {
                        List<PictureGson.NewsListBean> pictureList = new ArrayList<>();
                        for (PictureGson.NewsListBean newslistBean : pictureGson.getNewslist()) {
                            PictureGson.NewsListBean m1 = new PictureGson.NewsListBean();
                            m1.setTitle(newslistBean.getTitle());
                            m1.setCtime(newslistBean.getCtime());
                            m1.setDescription(newslistBean.getDescription());
                            m1.setPicUrl(newslistBean.getPicUrl());
                            m1.setUrl(newslistBean.getUrl());
                            pictureList.add(m1);
                        }

                        return pictureList;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PictureGson.NewsListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(List<PictureGson.NewsListBean> newsListBeans) {
                        listener.onSuccess(newsListBeans);
                    }
                });
    }

}
