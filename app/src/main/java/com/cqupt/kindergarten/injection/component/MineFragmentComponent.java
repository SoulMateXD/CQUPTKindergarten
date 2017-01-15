package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.MineFragmentModule;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.MineFragment;

import dagger.Component;

/**
 * Created by inferno on 2017/1/14.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = MineFragmentModule.class)
public interface MineFragmentComponent{
    void inject(MineFragment mineFragment);
     MineFragmentPresenter mineFragmentPresenter();
}
