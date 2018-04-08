package com.example.newsdemo.news;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsdemo.R;
import com.example.newsdemo.data.entity.NewsGson;
import com.example.newsdemo.util.PictureUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunchang on 2018/4/8.
 */

public class NewsClassFragment extends Fragment implements NewsContract.View {

    private NewsContract.Presenter mPresenter;
    private NewsAdapter mAdapter;
    private EasyRecyclerView mRecyclerView;
    private int mPageIndex = 1;

    private int mType;

    private static final String TAG = "NewsClassFragment";

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

        mAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadData(mType, mPageIndex++);
            }
        });

//        mRecyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mRecyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mPageIndex = 0;
//                        mPresenter.loadData(mType, mPageIndex);
//                    }
//                }, 1000);
//            }
//        });

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<>();
                String picUrl = mAdapter.getAllData().get(position).getPicUrl();
                Log.d(TAG, "onItemClick: " + picUrl);
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

    public class NewsAdapter extends RecyclerArrayAdapter<NewsGson.NewsBean> {

        public NewsAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return new NewsViewHolder(parent);
        }
    }

    public class NewsViewHolder extends BaseViewHolder<NewsGson.NewsBean> {

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

    private void lazyFetchData() {
        mPresenter.loadData(mType, mPageIndex);
        mPageIndex++;
    }
}
