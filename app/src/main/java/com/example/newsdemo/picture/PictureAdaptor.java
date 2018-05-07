package com.example.newsdemo.picture;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.newsdemo.data.entity.PictureGson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by yunchang on 2018/5/7.
 */

public class PictureAdaptor extends RecyclerArrayAdapter {

    public PictureAdaptor(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureViewHolder(parent);
    }

    public static class PictureViewHolder extends BaseViewHolder<PictureGson.NewsListBean> {

        ImageView mImageView;

        public PictureViewHolder(ViewGroup parent) {
            // 先创建new ImageView
            super(new ImageView(parent.getContext()));
            mImageView = (ImageView) itemView;
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        @Override
        public void setData(PictureGson.NewsListBean data) {
            final DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            Glide.with(getContext())
                    .load(data.getPicUrl())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = displayMetrics.widthPixels / 3 - 6;
                            int height = resource.getHeight() * width / resource.getWidth();

                            ViewGroup.LayoutParams params = mImageView.getLayoutParams();
                            params.height = height;
                            params.width =width;
                            mImageView.setLayoutParams(params);
                            mImageView.setImageBitmap(resource);
                        }
                    });
        }
    }
}
