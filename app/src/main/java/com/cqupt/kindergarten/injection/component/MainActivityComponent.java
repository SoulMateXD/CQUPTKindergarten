package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.MainActivityModule;
import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);

    MainActivityPresenter mainActivityPresenter();
}
