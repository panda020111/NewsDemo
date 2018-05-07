package com.example.newsdemo.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.newsdemo.R;
import java.util.ArrayList;

/**
 * Created by yunchang on 2018/5/7.
 */

public class NewsDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_detail_act);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("新闻详情");
        setSupportActionBar(toolbar);
        ImageView imageView = findViewById(R.id.ivImage);
        WebView webView = findViewById(R.id.web_text);

        //设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //获取跳转数据
        Bundle bundle = getIntent().getExtras();
        final ArrayList<String> data = bundle.getStringArrayList("data");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webView.loadUrl(data.get(1));
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                Log.d(TAG, "onPageFinished: " + url);
//                super.onPageFinished(view, url);
//            }
//        });

        Glide.with(this).load(data.get(0))
                .fitCenter().into(imageView);

    }
}
