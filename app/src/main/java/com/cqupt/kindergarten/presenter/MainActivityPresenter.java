package com.cqupt.kindergarten.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.cqupt.kindergarten.base.BasePresenter;
import com.cqupt.kindergarten.ui.activity.MainActivity;
import com.cqupt.kindergarten.ui.fragment.ClassFragment;
import com.cqupt.kindergarten.ui.fragment.HomeFragment;
import com.cqupt.kindergarten.ui.fragment.LoginFragment;
import com.cqupt.kindergarten.ui.fragment.MineFragment;
import com.cqupt.kindergarten.ui.fragment.NewsFragment;
import com.cqupt.kindergarten.ui.fragment.RegisteredFragment;
import com.cqupt.kindergarten.ui.ui_interface.IMainActivityInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends BasePresenter<IMainActivityInterface>{

    private static final String FRAGMENT_REPLACE = "com.cqupt.kindergarten.registerFragment.replace";
    private static final String FRAGMENT_BACK = "com.cqupt.kindergarten.LoginFragment.replace";
    private static final String MINE_FRAGMENT_REPLACE = "com.cqupt.kindergarten.MineFragment.replace";

    private BroadcastReceiver mBroadcastReceiver;

    //    public MainActivityPresenter(DataManager dataManager){
    //    }
    public MainActivityPresenter(){
    }

    public List<Fragment> getFragment(int userType){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(ClassFragment.newInstance(userType));
        fragments.add(new MineFragment());
        return fragments;
    }

    public void registerLocalBroadcast(final MainActivity mainActivity){
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(mainActivity);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(FRAGMENT_REPLACE);
        intentFilter.addAction(FRAGMENT_BACK);
        intentFilter.addAction(MINE_FRAGMENT_REPLACE);
        mBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, final Intent intent){
                final String action = intent.getAction();
                switch(action){
                    case FRAGMENT_REPLACE:
                        mainActivity.replaceFragment(3, new RegisteredFragment());
                        System.out.println("接受成功");
                        break;
                    case FRAGMENT_BACK:
                        mainActivity.replaceFragment(3, new LoginFragment());
                        break;
                    case MINE_FRAGMENT_REPLACE:
                        mainActivity.replaceFragment(3, new MineFragment());
                        break;
                }
            }
        };
        broadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }
}
