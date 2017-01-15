package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.model.DataManager;
import com.cqupt.kindergarten.presenter.NewsFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.NewsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhx on 2017/1/15.
 */
@Module
public class NewsFragmentModule {
    private NewsFragment mNewsFragment;

    public NewsFragmentModule(NewsFragment newsFragment){
        mNewsFragment = newsFragment;
    }

    @Provides
    public NewsFragmentPresenter providesNewsFragmentPresenter(DataManager dataManager){
        return new NewsFragmentPresenter(dataManager);
    }
}
