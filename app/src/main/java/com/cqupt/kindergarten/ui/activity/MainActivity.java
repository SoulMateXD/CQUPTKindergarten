package com.cqupt.kindergarten.ui.activity;


import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.injection.component.DaggerMainActivityComponent;
import com.cqupt.kindergarten.injection.component.MainActivityComponent;
import com.cqupt.kindergarten.injection.module.MainActivityModule;
import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.ui.ui_interface.IMainActivityInterface;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IMainActivityInterface {

    @Inject
    MainActivityPresenter mMainActivityPresenter;
    private MainActivityComponent mMainActivityComponent;


    @Override
    public void setUpComponent ( ) {
        if ( null == mMainActivityComponent ) {
            mMainActivityComponent = DaggerMainActivityComponent.builder ( )
                    .applicationComponent ( KindergartenApplication.get ( this ).getApplicationComponent ( ) )
                    .mainActivityModule ( new MainActivityModule ( this ) )
                    .build ( );
            mMainActivityComponent.inject ( this );
        }

    }

    @Override
    public int getLayoutId ( ) {
        return R.layout.activity_main;
    }

    @Override
    public void initView ( ) {
    }

    @Override
    public void initData ( ) {
    }


}
