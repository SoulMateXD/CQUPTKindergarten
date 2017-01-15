package com.cqupt.kindergarten.injection.module;

import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.fragment.NewsListFragment;
import com.cqupt.kindergarten.ui.fragment.NoticeListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhx on 2017/1/15.
 */
@Module
public class NoticeListFragmentModule {
    private NoticeListFragment mNoticesListFragment;

    public NoticeListFragmentModule(NoticeListFragment noticeListFragment){
        mNoticesListFragment = noticeListFragment;
    }

    @Provides
    public NewsListFragmentPresenter providesNewsListFragmentPresenter(){
        return new NewsListFragmentPresenter();
    }

}
