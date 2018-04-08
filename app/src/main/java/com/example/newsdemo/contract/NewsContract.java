package com.example.newsdemo.contract;

import com.example.newsdemo.entity.NewsGson;

import java.util.List;

/**
 * Created by yunchang on 2018/4/8.
 */

public interface NewsContract {


    interface View {
        void returnData(List<NewsGson.NewsBean> datas);
    }

    interface Presenter {
        void loadData(int type, int page);
    }

    interface onLoadDataListener {
        void  onSuccess(List<NewsGson.NewsBean> list);
        void  onFailure(String str,Throwable e);
    }

}
