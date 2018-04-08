package com.example.newsdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by yunchang on 2018/4/8.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;
    private Fragment[] mFragments;

    public TabPagerAdapter(FragmentManager fm, String[] tabTitles, Fragment[] fragments) {
        super(fm);
        mTabTitles = tabTitles;
        mFragments = fragments;
    }

    public void setTabTitles(String[] tabTitles) {
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position % mFragments.length];
    }

    @Override
    public int getCount() {
        if (mFragments == null || mFragments.length == 0) {
            return 0;
        }
        return mFragments.length;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTabTitles == null) {
            return "";
        }
        return mTabTitles[position];
    }
}
