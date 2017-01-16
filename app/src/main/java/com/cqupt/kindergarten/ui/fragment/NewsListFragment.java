package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.NewsListAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerNewsListFragmentComponent;
import com.cqupt.kindergarten.injection.component.NewsListFragmentComponent;
import com.cqupt.kindergarten.injection.module.NewsListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsListFragment;
import com.cqupt.kindergarten.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/1/15.
 */

public class NewsListFragment extends BaseFragment implements INewsListFragment, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NewsListFragmentPresenter mNewsListFragmentPresenter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    LinearLayoutManager mLinearLayoutManager;
    NewsListAdapter mAdapter;
    List<String> newsList;

    private NewsListFragmentComponent mNewsListFragmentComponent;

    public static NewsListFragment newInstance() {

        Bundle args = new Bundle();
        NewsListFragment mFragment = new NewsListFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    public void initView() {
        newsList = new ArrayList<>();
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.selector);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NewsListAdapter(getContext(), newsList);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mAdapter.setOnItemClickLitener(new NewsListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShortToast("您点击了第"+position+"个内容");
            }
        });
        ///mSwipeRefreshWidget.setRefreshing(true);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void getNews() {

    }

    @Override
    public void setUpComponent() {
        if (mNewsListFragmentComponent == null) {
            mNewsListFragmentComponent = DaggerNewsListFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsListFragmentModule(new NewsListFragmentModule(this))
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

    @Override
    public void onRefresh() {
            mSwipeRefreshWidget.setRefreshing(false);
    }
}
