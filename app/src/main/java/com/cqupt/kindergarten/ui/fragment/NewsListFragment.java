package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerNewsListFragmentComponent;
import com.cqupt.kindergarten.injection.component.NewsListFragmentComponent;
import com.cqupt.kindergarten.injection.module.NewsListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsListFragment;

import javax.inject.Inject;

/**
 * Created by lenovo on 2017/1/15.
 */

public class NewsListFragment extends BaseFragment implements INewsListFragment{

    @Inject
    NewsListFragmentPresenter mNewsListFragmentPresenter;

    private NewsListFragmentComponent mNewsListFragmentComponent;
    public static NewsListFragment newInstance() {

        Bundle args = new Bundle();
        NewsListFragment mFragment = new NewsListFragment();
        mFragment.setArguments(args);
        return mFragment;
    }
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }



    @Override
    public void getNews() {

    }

    @Override
    public void setUpComponent() {
        if(mNewsListFragmentComponent == null){
            mNewsListFragmentComponent = DaggerNewsListFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsListFragmentModule(new NewsListFragmentModule(this))
                    .build();
        }
    }
}
