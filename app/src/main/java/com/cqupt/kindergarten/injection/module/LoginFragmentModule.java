package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.model.DataManager;
import com.cqupt.kindergarten.presenter.LoginFragmentPresenter;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.LoginFragment;
import com.cqupt.kindergarten.ui.fragment.MineFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by inferno on 2017/1/14.
 */
@Module
public class LoginFragmentModule{
    private  LoginFragment mLoginFragment;

    public LoginFragmentModule(LoginFragment loginFragment){
        mLoginFragment = loginFragment;
    }

    @Provides
    public LoginFragmentPresenter providesLoginFragmentPresenter(DataManager dataManager){
        return new LoginFragmentPresenter(dataManager);
    }
}
