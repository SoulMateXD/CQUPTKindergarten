package com.cqupt.kindergarten.injection.component;

import android.app.Application;
import android.content.Context;

import com.cqupt.kindergarten.injection.module.ApplicationModule;
import com.cqupt.kindergarten.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Application application();

    Context context();

    DataManager dataManager();

}
