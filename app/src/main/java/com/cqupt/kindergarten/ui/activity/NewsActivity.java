package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.ui.fragment.NewsListFragment;
import com.cqupt.kindergarten.ui.fragment.NoticeListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/1/15.
 */

public class NewsActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragments;
    private List<String> mTitles;

    @Override
    public void setUpComponent() {

    }

    @Override
    public void initView() {
        getSupportActionBar();
        mToolbar.setTitle("新闻与公告");
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        mFragments.add(NewsListFragment.newInstance());
        mFragments.add(NoticeListFragment.newInstance());
        mTitles.add("校园公告");
        mTitles.add("校园新闻");
        mViewPager.setAdapter(new NewsAdapter(getSupportFragmentManager(),mFragments,mTitles));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_news;
    }

    class NewsAdapter extends FragmentStatePagerAdapter {

        private List<BaseFragment> mFragments;

        private List<String>mTitles;
        public NewsAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String>mTitles) {
            super(fm);
            this.mFragments = fragments;
            this.mTitles = mTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
