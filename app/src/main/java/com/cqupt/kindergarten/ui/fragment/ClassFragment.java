package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.ClassFragmentComponent;
import com.cqupt.kindergarten.injection.component.DaggerClassFragmentComponent;
import com.cqupt.kindergarten.injection.component.DaggerLoginFragmentComponent;
import com.cqupt.kindergarten.injection.component.LoginFragmentComponent;
import com.cqupt.kindergarten.injection.module.ClassFragmentModule;
import com.cqupt.kindergarten.injection.module.LoginFragmentModule;
import com.cqupt.kindergarten.presenter.ClassFragmentPresenter;
import com.cqupt.kindergarten.presenter.LoginFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.IClassFragmentInterface;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends BaseFragment implements IClassFragmentInterface{

    @Inject
    ClassFragmentPresenter mClassFragmentPresenter;

    private ClassFragmentComponent mClassFragmentComponent;

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
        if(null == mClassFragmentComponent){
            mClassFragmentComponent = DaggerClassFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .classFragmentModule(new ClassFragmentModule(this))
                    .build();
            mClassFragmentComponent.inject(this);
        }
    }

}
