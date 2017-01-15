package com.cqupt.kindergarten.ui.fragment;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerNewsFragmentComponent;
import com.cqupt.kindergarten.injection.component.NewsFragmentComponent;
import com.cqupt.kindergarten.injection.module.NewsFragmentModule;
import com.cqupt.kindergarten.presenter.NewsFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsFragmentInterface;

import javax.inject.Inject;

/**
 * Created by zhx on 2017/1/15.
 */

public class NewsFragment extends BaseFragment implements INewsFragmentInterface{
    @Inject
    NewsFragmentPresenter mNewsFragmentPresenter;
    private NewsFragmentComponent mNewsFragmentComponent;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {
        if(mNewsFragmentComponent == null){
            mNewsFragmentComponent = DaggerNewsFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsFragmentModule(new NewsFragmentModule(this))
                    .build();
            mNewsFragmentComponent.inject(this);
        }
    }
}
