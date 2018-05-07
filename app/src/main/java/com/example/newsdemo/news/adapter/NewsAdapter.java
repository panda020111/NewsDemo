package com.example.newsdemo.news.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsdemo.R;
import com.example.newsdemo.data.entity.NewsGson;
import com.example.newsdemo.util.PictureUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by yunchang on 2018/5/7.
 */

public class NewsAdapter extends RecyclerArrayAdapter<NewsGson.NewsBean>{

    private static final String TAG = "NewsAdapter";

    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent);
    }

    public static class NewsViewHolder extends BaseViewHolder<NewsGson.NewsBean> {

        private TextView mTv_name;
        private TextView mTv_sign;
        private ImageView mImg_face;


        public NewsViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.news_recycle_item);
            mTv_name = $(R.id.person_name);
            mTv_sign = $(R.id.person_sign);
            mImg_face = $(R.id.person_face);
        }

        @Override
        public void setData(final NewsGson.NewsBean data) {
            mTv_name.setText(data.getTitle());
            mTv_sign.setText(data.getCtime());
            PictureUtil.showImage(mImg_face, getContext(), data.getPicUrl());
            Log.d(TAG, "setData getPic url: " + data.getPicUrl());
        }
    }

}
