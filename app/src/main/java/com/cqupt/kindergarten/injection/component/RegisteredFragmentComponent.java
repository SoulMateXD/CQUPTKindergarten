package com.cqupt.kindergarten.injection.component;

import com.cqupt.kindergarten.injection.module.RegisteredFragmentModule;
import com.cqupt.kindergarten.presenter.RegisteredFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.RegisteredFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = RegisteredFragmentModule.class)
public interface RegisteredFragmentComponent{
    void inject(RegisteredFragment registeredFragment);

    RegisteredFragmentPresenter registeredFragmentPresenter();
}
