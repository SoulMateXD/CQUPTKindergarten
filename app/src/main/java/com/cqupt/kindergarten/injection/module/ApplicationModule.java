package com.cqupt.kindergarten.injection.module;

import android.app.Application;
import android.content.Context;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.model.DataManager;
import com.cqupt.kindergarten.model.data.DbOpenHelper;
import com.cqupt.kindergarten.model.helper.DatabaseOperatorHelper;
import com.cqupt.kindergarten.model.helper.PreferencesOperatorHelper;
import com.cqupt.kindergarten.model.model_interface.INetworkOperator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule{
    private KindergartenApplication mKindergartenApplication;
    private Context mContext;

    public ApplicationModule(KindergartenApplication application) {
        mKindergartenApplication = application;
        mContext = application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mKindergartenApplication;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    INetworkOperator provideINetworkOperator() {
        return INetworkOperator.Creator.newINetworkOperator();
    }

    @Provides
    @Singleton
    DatabaseOperatorHelper provideDatabaseOperatorHelper(DbOpenHelper dbOpenHelper) {
        return new DatabaseOperatorHelper(dbOpenHelper);
    }

    @Provides
    @Singleton
    PreferencesOperatorHelper providePreferencesOperatorHelper() {
        return new PreferencesOperatorHelper(mKindergartenApplication);
    }

    @Provides
    @Singleton
    DataManager provideDataManager(DatabaseOperatorHelper databaseOperatorHelper, INetworkOperator networkOperatorHelper, PreferencesOperatorHelper preferencesOperatorHelper) {
        return new DataManager(databaseOperatorHelper, networkOperatorHelper, preferencesOperatorHelper);
    }

    @Provides
    @Singleton
    DbOpenHelper provideDbOpenHelper() {
        return new DbOpenHelper(mContext);
    }
}
