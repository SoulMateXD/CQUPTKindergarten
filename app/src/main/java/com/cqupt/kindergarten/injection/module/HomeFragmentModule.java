package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.HomeFragmentPresenter;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.HomeFragment;
import com.cqupt.kindergarten.ui.fragment.MineFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhx on 2017/1/15.
 */
@Module
public class HomeFragmentModule {
    private HomeFragment mHomeFragment;

    public HomeFragmentModule(HomeFragment homeFragment){
        mHomeFragment = homeFragment;
    }

    @Provides
    public HomeFragmentPresenter providesHomeFragmentPresenter(){
        return new HomeFragmentPresenter();
    }
}
