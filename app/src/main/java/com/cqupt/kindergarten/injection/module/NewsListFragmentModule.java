package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.NewsListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhx on 2017/1/15.
 */
@Module
public class NewsListFragmentModule {
    private NewsListFragment mNewsListFragment;

    public NewsListFragmentModule(NewsListFragment newsListFragment){
        mNewsListFragment = newsListFragment;
    }

    @Provides
    public NewsListFragmentPresenter providesNewsListFragmentPresenter(){
        return new NewsListFragmentPresenter();
    }

}
