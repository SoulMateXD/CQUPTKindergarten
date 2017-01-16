package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.ClassFragmentModule;
import com.cqupt.kindergarten.injection.module.MineFragmentModule;
import com.cqupt.kindergarten.presenter.ClassFragmentPresenter;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.ClassFragment;
import com.cqupt.kindergarten.ui.fragment.MineFragment;

import dagger.Component;

/**
 * Created by inferno on 2017/1/14.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ClassFragmentModule.class)
public interface ClassFragmentComponent{
    void inject(ClassFragment classFragment);

    ClassFragmentPresenter classFragmentPresenter();
}
