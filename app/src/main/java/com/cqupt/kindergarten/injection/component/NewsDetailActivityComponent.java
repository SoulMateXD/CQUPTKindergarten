package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.NewsDetailActivityModule;
import com.cqupt.kindergarten.presenter.NewsDetailActivityPresenter;
import com.cqupt.kindergarten.ui.activity.NewsDetailActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = NewsDetailActivityModule.class)
public interface NewsDetailActivityComponent {
    void inject(NewsDetailActivity newsDetailActivity);

    NewsDetailActivityPresenter NewsDetailActivityPresenter();
}
