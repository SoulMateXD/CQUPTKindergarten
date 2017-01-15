package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.MineFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by inferno on 2017/1/14.
 */
@Module
public class MineFragmentModule{
    private MineFragment mMineFragment;

    public MineFragmentModule(MineFragment mineFragment){
        mMineFragment = mineFragment;
    }

    @Provides
    public MineFragmentPresenter providesMineFragmentPresenter(){
        return new MineFragmentPresenter();
    }
}
