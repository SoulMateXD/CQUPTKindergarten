package com.cqupt.kindergarten.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.ui.fragment.NewsListFragment;
import com.cqupt.kindergarten.ui.fragment.NoticeListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

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
    @BindView(R.id.toolbar_image)
    ImageView toolbarImage;

    private List<BaseFragment> mFragments;
    private List<String> mTitles;
    private int type;
    private int intentType;
    private boolean isParent;
    private SharedPreferences preferences;


    private static final int TEACHER = 0;
    private static final int PARENT = 1;
    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final String IS_LOGIN = "isLogin";
    /*
    *  class，和 news 两个fragment中，公告和图鉴模块，用于跳转判断
    * */
    public static int TYPE_CLASS = 0;
    public static int TYPE_NEWS = 1;


    @Override
    public void setUpComponent() {

    }

    @Override
    public void initView() {
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();

        //初始化toolbar
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //初始化intentType相关的数据
        intentType = getIntent().getIntExtra("intentType", 0);
        if (intentType == TYPE_NEWS){
            mTitles.add("校园新闻");
            mTitles.add("校园公告");
        }else if (intentType == TYPE_CLASS){
            mTitles.add("班级新闻");
            mTitles.add("班级公告");
        }

        //配置顶部tab
        mFragments.add(NewsListFragment.newInstance());
        mFragments.add(NoticeListFragment.newInstance());
        mViewPager.setAdapter(new NewsAdapter(getSupportFragmentManager(), mFragments, mTitles));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);


        loadLoginDataFromPreferences();
        toolbarImage.setVisibility(View.GONE);
        isParent = (type == PARENT);


        /*
        *  目前Android端不要发公告功能
        * */
//        // 页面切换时，如果是校园新闻则隐藏添加公告按钮，若是公告则显示
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(position == 0){
//                    toolbarImage.setVisibility(GONE);
//                }else {
//                    if (!isParent)
//                        toolbarImage.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
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

        private List<String> mTitles;

        public NewsAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> mTitles) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.toolbar_image)
    public void onViewClicked() {
        startActivity(new Intent(NewsActivity.this, SendNoticeActivity.class));
    }

    private void loadLoginDataFromPreferences() {
        preferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        type = preferences.getInt("TYPE", 0);
    }
}
