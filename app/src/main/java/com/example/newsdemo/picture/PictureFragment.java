package com.example.newsdemo.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsdemo.R;
import com.example.newsdemo.data.entity.PictureGson;
import com.example.newsdemo.util.PixUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.List;

import static com.jude.easyrecyclerview.EasyRecyclerView.TAG;

/**
 * Created by yunchang on 2018/5/7.
 */

public class PictureFragment extends Fragment implements PictureContract.View {

    private EasyRecyclerView mRecyclerView;
    private PictureAdaptor mAdapter;

    private PicturePresenter mPresenter;
    private int mPage = 1;
    private boolean isViewPrepared = false;
    private boolean hasFetchData;

    public static PictureFragment newInstance () {
        PictureFragment fragment = new PictureFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.picture_frag, container, false);

        mPresenter = new PicturePresenter(this);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new PictureAdaptor(getActivity()));

        //设置边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertToPixel(10, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        mRecyclerView.addItemDecoration(itemDecoration);


        //更多加载
        mAdapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                //
                Log.e("更多", "更多");
                mPresenter.loadData(mPage);
                mPage++;
            }

            @Override
            public void onMoreClick() {

            }
        });

        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick:  position ====>" + position);
            }
        });

        isViewPrepared = true;

        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void returnData(List<PictureGson.NewsListBean> datas) {
        mAdapter.addAll(datas);
    }

    private void lazyFetchDataIfPrepared() {
        if (isViewPrepared && getUserVisibleHint() && !hasFetchData) {
            lazyFetchData();
            hasFetchData = true;
        }
    }

    private void lazyFetchData() {
        mPresenter.loadData(mPage);
        mPage++;
    }

}
