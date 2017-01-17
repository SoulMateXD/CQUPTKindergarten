package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.presenter.TimeTablePresenter;
import com.cqupt.kindergarten.ui.activity.MainActivity;
import com.cqupt.kindergarten.ui.activity.TimeTableActivity;

import dagger.Module;
import dagger.Provides;


@Module
public class TimeTableActivityModule {
    private TimeTableActivity mTimeTableActivity;
    public TimeTableActivityModule(TimeTableActivity timeTableActivity){
        mTimeTableActivity = timeTableActivity;
    }
    @Provides
    public TimeTableActivity provideTimeTableActivity(){
        return mTimeTableActivity;
    }
    @Provides
    public TimeTablePresenter provideTimeTablePresenter(){
        return new TimeTablePresenter();
    }
}
