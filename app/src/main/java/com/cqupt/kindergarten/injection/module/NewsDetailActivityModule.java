package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.presenter.NewsDetailActivityPresenter;
import com.cqupt.kindergarten.ui.activity.MainActivity;
import com.cqupt.kindergarten.ui.activity.NewsDetailActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class NewsDetailActivityModule {
    private NewsDetailActivity mNewsDetailActivity;
    public NewsDetailActivityModule(NewsDetailActivity newsDetailActivity){
        mNewsDetailActivity= newsDetailActivity;
    }
    @Provides
    public NewsDetailActivity provideNewsDetailActivity(){
        return mNewsDetailActivity;
    }
    @Provides
    public NewsDetailActivityPresenter provideNewsDetailActivityPresenter(){
        return new NewsDetailActivityPresenter();
    }
}
