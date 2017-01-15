package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.HomeFragmentModule;
import com.cqupt.kindergarten.injection.module.MineFragmentModule;
import com.cqupt.kindergarten.presenter.HomeFragmentPresenter;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.HomeFragment;
import com.cqupt.kindergarten.ui.fragment.MineFragment;

import dagger.Component;

/**
 * Created by zhx on 2017/1/15.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {
    void inject(HomeFragment homeFragment);
     HomeFragmentPresenter homeFragmentPresenter();
}
