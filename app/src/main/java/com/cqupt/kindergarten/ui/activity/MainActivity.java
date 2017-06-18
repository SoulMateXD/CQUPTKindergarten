package com.cqupt.kindergarten.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.MainViewPagerAdapter;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.injection.component.DaggerMainActivityComponent;
import com.cqupt.kindergarten.injection.component.MainActivityComponent;
import com.cqupt.kindergarten.injection.module.MainActivityModule;
import com.cqupt.kindergarten.presenter.MainActivityPresenter;
import com.cqupt.kindergarten.ui.fragment.LoginFragment;
import com.cqupt.kindergarten.ui.fragment.MineFragment;
import com.cqupt.kindergarten.ui.ui_interface.IMainActivityInterface;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivityInterface, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, View.OnTouchListener{

    @Inject
    MainActivityPresenter mMainActivityPresenter;

    @BindView(R.id.main_viewPager)
    ViewPager mMainViewPager;

    @BindView(R.id.main_bottom)
    BottomNavigationView mMainBottom;

    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;

    private MainActivityComponent mMainActivityComponent;
    private MainViewPagerAdapter mMainViewPagerAdapter;
    private MenuItem menuItem;
    private boolean fragmentsUpdateFlag[] = {false, false, false};
    private List<Fragment> mFragment;
    private int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setUpComponent(){
        if(null == mMainActivityComponent){
            mMainActivityComponent = DaggerMainActivityComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .mainActivityModule(new MainActivityModule(this))
                    .build();
            mMainActivityComponent.inject(this);
        }
    }

    @Override
    public void initView(){
        mMainToolbar.setTitle("");
    }

    @Override
    public void initData(){
        Intent intent = getIntent();
        userType = intent.getIntExtra("TYPE", 0);
        mFragment = mMainActivityPresenter.getFragment(userType);
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mFragment);
        mMainBottom.setOnNavigationItemSelectedListener(this);
        mMainViewPager.addOnPageChangeListener(this);
        mMainViewPager.setAdapter(mMainViewPagerAdapter);
        mMainViewPager.setOnTouchListener(this);
        mMainActivityPresenter.registerLocalBroadcast(this);
    }

    @Override
    public int getLayoutID(){
        return R.layout.activity_main;
    }

    //底部选择事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.news:
                mMainViewPager.setCurrentItem(0);
                break;
            case R.id.class_pic:
                mMainViewPager.setCurrentItem(1);
                break;
            case R.id.mine:
                mMainViewPager.setCurrentItem(2);
                break;
        }
        return false;
    }

    //viewpager滑动事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){

    }

    @Override
    public void onPageSelected(int position){
        if(menuItem != null){
            menuItem.setChecked(false);
        } else{
            mMainBottom.getMenu().getItem(0).setChecked(false);
        }
        menuItem = mMainBottom.getMenu().getItem(position);
        menuItem.setChecked(true);

    }

    @Override
    public void onPageScrollStateChanged(int state){

    }

    //禁止viewpager滑动
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        return true;
    }

    @Override
    public void replaceFragment(int position, Fragment fragment){
        fragmentsUpdateFlag[position] = true;
        //替换fragment
        mMainViewPagerAdapter.setFragmentsUpdateFlag(fragmentsUpdateFlag);
        mFragment.set(position, fragment);
        mMainViewPagerAdapter.notifyDataSetChanged();
    }
}
