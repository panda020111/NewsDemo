package com.example.newsdemo.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsdemo.R;
import com.example.newsdemo.data.entity.NewsGson;
import com.example.newsdemo.news.adapter.NewsAdapter;
import com.example.newsdemo.util.PictureUtil;
import com.example.newsdemo.util.PixUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunchang on 2018/4/8.
 */

public class NewsClassFragment extends Fragment implements NewsContract.View {

    private static final String TAG = "NewsClassFragment";
    private NewsContract.Presenter mPresenter;
    private NewsAdapter mAdapter;
    private EasyRecyclerView mRecyclerView;
    private int mPageIndex = 1;
    private int mType;

    public static NewsClassFragment newInstance(int type) {
        NewsClassFragment fragment = new NewsClassFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mType = getArguments().getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_frag, container, false);

        mRecyclerView = view.findViewById(R.id.recycleView);
        mPresenter = new NewsPresenter(this, getContext());

        mRecyclerView.setAdapter(mAdapter = new NewsAdapter(getActivity()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 设置边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertToPixel(8, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);

        mAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadData(mType, mPageIndex++);
            }
        });

        // 刷新事件
        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 触发清空数据队列；
                Log.d(TAG, "onRefresh:  on refresh event emit");
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();
                        mPageIndex = 0;
                        mPresenter.loadData(mType, mPageIndex);
                    }
                }, 1000);

            }
        });

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<>();
                String picUrl = mAdapter.getAllData().get(position).getPicUrl();
                Log.d(TAG, "onItemClick: " + picUrl);
                data.add(picUrl);
                data.add(mAdapter.getAllData().get(position).getUrl());
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                Bundle bundle = new Bundle();

                // bundle带数据
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyFetchData();
    }

    @Override
    public void returnData(List<NewsGson.NewsBean> datas) {
        mAdapter.addAll(datas);
        Log.d(TAG, "returnData Size: " + datas.size());
    }

    private void lazyFetchData() {
        mPresenter.loadData(mType, mPageIndex);
        mPageIndex++;
    }
}
