package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.LoginFragmentModule;
import com.cqupt.kindergarten.presenter.LoginFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.LoginFragment;

import dagger.Component;

/**
 * Created by inferno on 2017/1/14.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = LoginFragmentModule.class)
public interface LoginFragmentComponent{
    void inject(LoginFragment loginFragment);
     LoginFragmentPresenter loginFragmentPresenter();
}
