package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.NewsFragmentModule;
import com.cqupt.kindergarten.presenter.NewsFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.NewsFragment;

import dagger.Component;

/**
 * Created by zhx on 2017/1/15.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = NewsFragmentModule.class)
public interface NewsFragmentComponent {
    void inject(NewsFragment newsFragment);
     NewsFragmentPresenter newsFragmentPresenter();
}
