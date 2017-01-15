package com.cqupt.kindergarten.ui.fragment;

import android.support.v4.app.Fragment;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerMineFragmentComponent;
import com.cqupt.kindergarten.injection.component.MineFragmentComponent;
import com.cqupt.kindergarten.injection.module.MineFragmentModule;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.IMineFragmentInterface;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements IMineFragmentInterface{

    @Inject
    MineFragmentPresenter mMineFragmentPresenter;

    private MineFragmentComponent mMineFragmentComponent;

    @Override
    public int getLayoutId(){
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(){

    }

    @Override
    public void initData(){

    }

    @Override
    public void setUpComponent(){
        if(mMineFragmentComponent == null){
            mMineFragmentComponent = DaggerMineFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .mineFragmentModule(new MineFragmentModule(this))
                    .build();
            mMineFragmentComponent.inject(this);
        }
    }

}
