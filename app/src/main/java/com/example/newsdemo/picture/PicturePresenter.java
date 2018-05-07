package com.example.newsdemo.picture;

import android.content.Context;
import android.util.Log;

import com.example.newsdemo.data.PictureModel;
import com.example.newsdemo.data.entity.PictureGson;

import java.util.List;

import static com.jude.easyrecyclerview.EasyRecyclerView.TAG;

/**
 * Created by yunchang on 2018/5/7.
 */

public class PicturePresenter implements PictureContract.Presenter, PictureContract.OnLoadDataListener {

    private PictureContract.View mView;
    private PictureModel mModel;

    public PicturePresenter(PictureContract.View view) {
        mView = view;
        mModel = new PictureModel();
    }

    @Override
    public void loadData(int page) {
        mModel.loadData(this, page);
    }

    @Override
    public void onSuccess(List<PictureGson.NewsListBean> list) {
        mView.returnData(list);
    }

    @Override
    public void onFailure(String str, Exception e) {
        Log.d(TAG, "onFailure: ====>" + str);
    }
}
