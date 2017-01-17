package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.MainActivityModule;
import com.cqupt.kindergarten.injection.module.TimeTableActivityModule;
import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.ui.activity.MainActivity;
import com.cqupt.kindergarten.ui.activity.TimeTableActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = TimeTableActivityModule.class)
public interface TimeTableActivityComponent {
    void inject(TimeTableActivity timeTableActivity);

}
