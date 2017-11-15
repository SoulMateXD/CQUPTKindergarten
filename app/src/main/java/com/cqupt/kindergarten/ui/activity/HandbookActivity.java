package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.ui.fragment.HandBookAlbumFragment;
import com.cqupt.kindergarten.ui.fragment.HandBookVideoFragment;
import com.cqupt.kindergarten.ui.fragment.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandbookActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.handbook_viewpager)
    ViewPager handbookViewpager;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    /*
    *  class，和 news 两个fragment中，公告和图鉴模块，用于跳转判断
    * */
    public static int TYPE_CLASS = 0;
    public static int TYPE_NEWS = 1;
    public static int TYPE_COLLECTION = 2;

    private int intentType;
    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handbook);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        //初始化toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        //初始化intenType相关的数据
        intentType = getIntent().getIntExtra("intentType", 0);
        if (intentType == TYPE_CLASS){
            getSupportActionBar().setTitle("");
            toolbarTitle.setText("班级活动照片/视频");
            mTitles.add("班级图片");
            mTitles.add("班级视频");
        }else if (intentType == TYPE_NEWS){
            getSupportActionBar().setTitle("");
            toolbarTitle.setText("全员大型活动照片/视频");
            mTitles.add("校园图片");
            mTitles.add("校园视频");
        }else if (intentType == TYPE_COLLECTION){
            getSupportActionBar().setTitle("");
            toolbarTitle.setText("我的收藏");
            mTitles.add("图片收藏");
            mTitles.add("视频收藏");
        }

        //初始化顶部tab
        mFragments.add(HandBookAlbumFragment.newInstance(intentType));
        mFragments.add(VideoListFragment.newInstance(intentType));
        handbookViewpager.setAdapter(new
                HandbookFragmentAdapter(getSupportFragmentManager(), mTitles, mFragments));
        handbookViewpager.setOffscreenPageLimit(2);
        handbookViewpager.setCurrentItem(0);
        tabLayout.setupWithViewPager(handbookViewpager);
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

    private class HandbookFragmentAdapter extends FragmentPagerAdapter {

        private List<String> mTitles;
        private List<Fragment> fragments;


        public HandbookFragmentAdapter(FragmentManager fm, List<String> mTitles, List<Fragment> fragments) {
            super(fm);
            this.mTitles = mTitles;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
