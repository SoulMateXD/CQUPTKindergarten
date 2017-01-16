package com.cqupt.kindergarten.ui.fragment;

import android.support.v4.app.Fragment;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.ClassFragmentComponent;
import com.cqupt.kindergarten.injection.component.DaggerClassFragmentComponent;
import com.cqupt.kindergarten.injection.module.ClassFragmentModule;
import com.cqupt.kindergarten.presenter.ClassFragmentPresenter;
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
        return R.layout.fragment_class;
    }

    @Override
    public void initView(){

    }

    @Override
    public void initData(){

    }

    @Override
    public void setUpComponent(){
        if(mClassFragmentComponent == null){
            mClassFragmentComponent = DaggerClassFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .classFragmentModule(new ClassFragmentModule(this))
                    .build();
            mClassFragmentComponent.inject(this);
        }
    }

}
