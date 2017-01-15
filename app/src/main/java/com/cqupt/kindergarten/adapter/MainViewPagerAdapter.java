package com.cqupt.kindergarten.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by inferno on 2017/1/15.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> mFragmentList;

    public MainViewPagerAdapter(FragmentManager manager, List<Fragment> fragments){
        super(manager);
        mFragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position){
        return mFragmentList.get(position);
    }

    @Override
    public int getCount(){
        return mFragmentList.size();
    }

}
