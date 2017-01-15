package com.cqupt.kindergarten.presenter;

import android.support.v4.app.Fragment;

import com.cqupt.kindergarten.base.BasePresenter;
import com.cqupt.kindergarten.ui.fragment.ClassFragment;
import com.cqupt.kindergarten.ui.fragment.HomeFragment;
import com.cqupt.kindergarten.ui.fragment.LoginFragment;
import com.cqupt.kindergarten.ui.fragment.NewsFragment;
import com.cqupt.kindergarten.ui.ui_interface.IMainActivityInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends BasePresenter<IMainActivityInterface>{
//    public MainActivityPresenter(DataManager dataManager){
//    }
    public MainActivityPresenter(){}

    public List<Fragment> getFragment(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new ClassFragment());
        fragments.add(new LoginFragment());
        return fragments;
    }
}
