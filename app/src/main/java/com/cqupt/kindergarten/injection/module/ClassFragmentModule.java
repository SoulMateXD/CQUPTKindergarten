package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.ClassFragmentPresenter;
import com.cqupt.kindergarten.presenter.HomeFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.ClassFragment;
import com.cqupt.kindergarten.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhx on 2017/1/15.
 */
@Module
public class ClassFragmentModule{
    private ClassFragment mClassFragment;

    public ClassFragmentModule(ClassFragment classFragment){
        mClassFragment = classFragment;
    }

    @Provides
    public ClassFragmentPresenter providesClassFragmentPresenter(){
        return new ClassFragmentPresenter();
    }
}
