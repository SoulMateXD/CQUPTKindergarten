package com.cqupt.kindergarten.ui.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.bean.NewsListBean;
import com.cqupt.kindergarten.injection.component.DaggerNewsDetailActivityComponent;
import com.cqupt.kindergarten.injection.component.NewsDetailActivityComponent;
import com.cqupt.kindergarten.injection.module.NewsDetailActivityModule;
import com.cqupt.kindergarten.presenter.NewsDetailActivityPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsDetailActivityInterface;
import com.cqupt.kindergarten.util.ScreenUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseActivity implements INewsDetailActivityInterface {


    @Inject
    NewsDetailActivityPresenter mNewsDetailActivityPresenterm;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    private NewsDetailActivityComponent mNewsDetailActivityComponent;
    @BindView(R.id.img_news)
    ImageView mImgNews;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_content)
    TextView mTvNewsContent;


    private NewsListBean bean;
    private static final String defaultPic = "http://pic.service.yaolan.com/32/20160906/98/1473134188002_1_w600_h400_o.jpg";
    //后台传过来的空图片
    private static final String defaultNullPic = "0";

    @Override
    public void setUpComponent() {
        if (mNewsDetailActivityComponent == null) {
            mNewsDetailActivityComponent = DaggerNewsDetailActivityComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsDetailActivityModule(new NewsDetailActivityModule(this))
                    .build();
            mNewsDetailActivityComponent.inject(this);
        }
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        bean = intent.getParcelableExtra("NewsListItem");


        setSupportActionBar(mToolbar);
        toolbarLayout.setTitle(bean.getTitle());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String imageUrl = defaultPic;
        if (!bean.getUrl1().equals(defaultNullPic)){
            imageUrl = bean.getUrl1();
        }
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.default_image)
                .into(mImgNews);

        RichText.initCacheDir(this);
        RichText.fromHtml(bean.getMessage())
                .scaleType(ImageHolder.ScaleType.FIT_CENTER)
                .fix(new ImageFixCallback() {
                    @Override
                    public void onInit(ImageHolder holder) {

                    }

                    @Override
                    public void onLoading(ImageHolder holder) {

                    }

                    @Override
                    public void onSizeReady(ImageHolder holder, int imageWidth, int imageHeight, ImageHolder.SizeHolder sizeHolder) {

                    }


                    @Override
                    public void onImageReady(ImageHolder holder, int width, int height) {
                        int ScreenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
                        if (ScreenWidth > width)
                            holder.setSize(width, height);
                    }

                    @Override
                    public void onFailure(ImageHolder holder, Exception e) {

                    }
                })//设置图片最大宽度
                .into(mTvNewsContent);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void getNewsDetail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
