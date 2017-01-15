package com.cqupt.kindergarten.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by inferno on 2017/1/15.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fragmentList;
    private boolean fragmentsUpdateFlag[] = {false, false, false, false};;

    private FragmentManager fm;

    public MainViewPagerAdapter(FragmentManager manager, List<Fragment> fragments){
        super(manager);
        fragmentList = fragments;
        fm = manager;
    }

    @Override
    public Fragment getItem(int position){
        return fragmentList.get(position % fragmentList.size());
    }

    @Override
    public int getCount(){
        return fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        //得到缓存的fragment

        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        //得到tag
        String fragmentTag = fragment.getTag();
        if(fragmentsUpdateFlag[position % fragmentsUpdateFlag.length]){
            //如果这个fragment需要更新
            FragmentTransaction ft = fm.beginTransaction();
            //移除旧的fragment
            ft.remove(fragment);
            //换成新的fragment
            fragment = fragmentList.get(position % fragmentList.size());
            //添加新fragment时必须用前面获得的tag
            ft.add(container.getId(), fragment, fragmentTag);
            ft.attach(fragment);
            ft.commit();
            //复位更新标志
            fragmentsUpdateFlag[position % fragmentsUpdateFlag.length] = false;
        }

        return fragment;
    }

    public void setFragmentsUpdateFlag(boolean[] fragmentsUpdateFlag){
        this.fragmentsUpdateFlag = fragmentsUpdateFlag;
    }
}

