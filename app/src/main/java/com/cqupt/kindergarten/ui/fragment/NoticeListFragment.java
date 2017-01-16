package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerNoticeListFragmentComponent;
import com.cqupt.kindergarten.injection.component.NoticeListFragmentComponent;
import com.cqupt.kindergarten.injection.module.NoticeListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsListFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/1/15.
 */

public class NoticeListFragment extends BaseFragment implements INewsListFragment {

    @Inject
    NewsListFragmentPresenter mNewsListFragmentPresenter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshWidget;

    private NoticeListFragmentComponent mNoticeListFragmentComponent;

    public static NoticeListFragment newInstance() {

        Bundle args = new Bundle();
        NoticeListFragment mFragment = new NoticeListFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @Override
    public void getNews() {

    }

    @Override
    public void setUpComponent() {
        if (mNoticeListFragmentComponent == null) {
            mNoticeListFragmentComponent = DaggerNoticeListFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .noticeListFragmentModule(new NoticeListFragmentModule(this))
                    .build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
