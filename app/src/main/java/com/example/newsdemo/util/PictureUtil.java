package com.example.newsdemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by yunchang on 2018/4/8.
 */

public class PictureUtil {

    public static void showImage(ImageView imageView, Context context, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .into(imageView);
    }
}
