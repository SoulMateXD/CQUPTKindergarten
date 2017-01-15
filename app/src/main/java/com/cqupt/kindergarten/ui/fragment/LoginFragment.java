package com.cqupt.kindergarten.ui.fragment;

import android.support.v4.app.Fragment;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerLoginFragmentComponent;
import com.cqupt.kindergarten.injection.component.LoginFragmentComponent;
import com.cqupt.kindergarten.injection.module.LoginFragmentModule;
import com.cqupt.kindergarten.presenter.LoginFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.ILoginFragmentInterface;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements ILoginFragmentInterface{

    @Inject
    LoginFragmentPresenter mLoginFragmentPresenter;

    private LoginFragmentComponent mLoginFragmentComponent;

    @Override
    public int getLayoutId(){
        return R.layout.fragment_login;
    }

    @Override
    public void initView(){

    }

    @Override
    public void initData(){

    }

    @Override
    public void setUpComponent(){
        if(null == mLoginFragmentComponent){
            mLoginFragmentComponent = DaggerLoginFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .loginFragmentModule(new LoginFragmentModule(this))
                    .build();
            mLoginFragmentComponent.inject(this);
        }
    }
}
