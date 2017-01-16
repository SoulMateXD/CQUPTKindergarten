package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.HomeFragmentPresenter;
import com.cqupt.kindergarten.presenter.RegisteredFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.HomeFragment;
import com.cqupt.kindergarten.ui.fragment.RegisteredFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhx on 2017/1/15.
 */
@Module
public class RegisteredFragmentModule{
    private RegisteredFragment mRegisteredFragment;

    public RegisteredFragmentModule(RegisteredFragment registeredFragment){
        mRegisteredFragment = registeredFragment;
    }

    @Provides
    public RegisteredFragmentPresenter providesRegisteredFragmentPresenter(){
        return new RegisteredFragmentPresenter();
    }
}
