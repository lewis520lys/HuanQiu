package com.lewis.cp.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lewis.cp.base.BaseFragment;

import java.util.List;

public class ContentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments;

    public ContentAdapter(FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}