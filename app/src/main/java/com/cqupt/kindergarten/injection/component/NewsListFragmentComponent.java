package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.NewsListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.NewsListFragment;
import com.cqupt.kindergarten.ui.fragment.NoticeListFragment;

import dagger.Component;

/**
 * Created by zhx on 2017/1/15.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = NewsListFragmentModule.class)
public interface NewsListFragmentComponent {
    void inject(NewsListFragment newsListFragment);
    NewsListFragmentPresenter NewsListFragmentPresenter();
}
