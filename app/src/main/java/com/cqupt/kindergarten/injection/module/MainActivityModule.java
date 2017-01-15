package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.ui.activity.MainActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class MainActivityModule {
    private MainActivity mMainActivity;
    public MainActivityModule(MainActivity mainActivity){
        mMainActivity = mainActivity;
    }
    @Provides
    public MainActivity provideMainActivity(){
        return mMainActivity;
    }
    @Provides
    public MainActivityPresenter provideMainActivityPresenter(){
        return new MainActivityPresenter();
    }
}
