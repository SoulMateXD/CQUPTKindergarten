package com.cqupt.kindergarten.ui.fragment;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerHomeFragmentComponent;
import com.cqupt.kindergarten.injection.component.HomeFragmentComponent;
import com.cqupt.kindergarten.injection.module.HomeFragmentModule;
import com.cqupt.kindergarten.presenter.HomeFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.IHomeFragmentInterface;

import javax.inject.Inject;

/**
 * Created by zhx on 2017/1/15.
 */

public class HomeFragment extends BaseFragment implements IHomeFragmentInterface{

    @Inject
    HomeFragmentPresenter mHomeFragmentPresenter;

    private HomeFragmentComponent mHomeFragmentComponent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {
        if(mHomeFragmentComponent == null){
            mHomeFragmentComponent = DaggerHomeFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .homeFragmentModule(new HomeFragmentModule(this))
                    .build();
            mHomeFragmentComponent.inject(this);
        }
    }
}
