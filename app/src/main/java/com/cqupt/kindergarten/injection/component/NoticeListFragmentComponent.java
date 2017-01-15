package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.NewsListFragmentModule;
import com.cqupt.kindergarten.injection.module.NoticeListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.NewsListFragment;
import com.cqupt.kindergarten.ui.fragment.NoticeListFragment;

import dagger.Component;

/**
 * Created by zhx on 2017/1/15.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = NoticeListFragmentModule.class)
public interface NoticeListFragmentComponent {
    void inject(NoticeListFragment NoticeListFragment);
    NewsListFragmentPresenter NewsListFragmentPresenter();
}
