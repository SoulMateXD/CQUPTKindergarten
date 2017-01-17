package com.cqupt.kindergarten.ui.activity;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.injection.component.DaggerMainActivityComponent;
import com.cqupt.kindergarten.injection.component.DaggerTimeTableActivityComponent;
import com.cqupt.kindergarten.injection.component.TimeTableActivityComponent;
import com.cqupt.kindergarten.injection.module.MainActivityModule;
import com.cqupt.kindergarten.injection.module.TimeTableActivityModule;
import com.cqupt.kindergarten.presenter.TimeTablePresenter;
import com.cqupt.kindergarten.ui.ui_interface.ITimeTableActivityInterface;

import javax.inject.Inject;

/**
 * Created by SoulMateXD on 2017/1/16.
 */

public class TimeTableActivity extends BaseActivity implements ITimeTableActivityInterface{
    @Inject
    TimeTablePresenter mTimeTablePresenter;
    private TimeTableActivityComponent mTimeTableActivityComponent;


    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutID() {
        return 0;
    }

    @Override
    public void getTimeTable() {

    }

    @Override
    public void setUpComponent() {
        if(null == mTimeTableActivityComponent){
            mTimeTableActivityComponent = DaggerTimeTableActivityComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .timeTableActivityModule(new TimeTableActivityModule(this))
                    .build();
            mTimeTableActivityComponent.inject(this);
        }
    }
}
