package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivityInterface, BottomNavigationView.OnNavigationItemSelectedListener, View.OnTouchListener{

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
    private boolean isLogin = false;

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
        mMainToolbar.setTitle("重邮幼儿园");
    }

    @Override
    public void initData(){
        //        mMainActivityPresenter = new MainActivityPresenter();
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), mMainActivityPresenter
                .getFragment());
        mMainBottom.setOnNavigationItemSelectedListener(this);
        mMainViewPager.setAdapter(mMainViewPagerAdapter);
        mMainViewPager.setOnTouchListener(this);
    }

    @Override
    public int getLayoutID(){
        return R.layout.activity_main;
    }

    //底部选择事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        switch(item.getItemId()){
            case R.id.main:
                mMainViewPager.setCurrentItem(0);
                break;
            case R.id.news:
                mMainViewPager.setCurrentItem(1);
                break;
            case R.id.class_pic:
                mMainViewPager.setCurrentItem(2);
                break;
            case R.id.mine:
                mMainViewPager.setCurrentItem(3);
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(MineFragment.class
                                                                                          .getName());
                if(fragment != null && isLogin){
                    getSupportFragmentManager().beginTransaction().show(fragment).commit();
                } else{
                    Fragment loginFragment = getSupportFragmentManager().findFragmentByTag(LoginFragment.class
                                                                                                   .getName());
                    if(loginFragment != null){
                        getSupportFragmentManager().beginTransaction().show(loginFragment).commit();
                    }
                }
                break;
        }
        return false;
    }

    //禁止viewpager滑动
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        return true;
    }
}
