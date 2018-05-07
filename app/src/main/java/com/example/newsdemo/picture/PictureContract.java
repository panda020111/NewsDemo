package com.example.newsdemo.picture;

import com.example.newsdemo.data.entity.PictureGson;

import java.util.List;

/**
 * Created by yunchang on 2018/5/7.
 */

public interface PictureContract {

    interface View {
        void returnData(List<PictureGson.NewsListBean> datas);
    }

    interface Presenter {
        void loadData(int page);
    }

    interface OnLoadDataListener {
        void onSuccess(List<PictureGson.NewsListBean> list);
        void onFailure(String str, Exception e);
    }
}
