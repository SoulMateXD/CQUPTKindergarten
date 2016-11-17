package com.cqupt.kindergarten;

import android.app.Application;
import android.content.Context;

import com.cqupt.kindergarten.injection.component.ApplicationComponent;
import com.cqupt.kindergarten.injection.component.DaggerApplicationComponent;
import com.cqupt.kindergarten.injection.module.ApplicationModule;


public class KindergartenApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplicationComponent();
    }

    public static KindergartenApplication get(Context context) {
        return (KindergartenApplication) context.getApplicationContext();
    }

    public void setApplicationComponent() {
        if (null == mApplicationComponent) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
    }

    public ApplicationComponent getApplicationComponent() {
        if (null == mApplicationComponent) {
            throw new RuntimeException("ApplicationComponent is null");
        }
        return mApplicationComponent;
    }
}
