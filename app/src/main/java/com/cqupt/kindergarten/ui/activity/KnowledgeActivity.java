package com.cqupt.kindergarten.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.KnowledgeAdapter;
import com.cqupt.kindergarten.adapter.NewsListAdapter;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.ui.ui_interface.INewsDetailActivityInterface;
import com.cqupt.kindergarten.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KnowledgeActivity extends BaseActivity implements INewsDetailActivityInterface, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.img_news)
    ImageView mImgNews;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    LinearLayoutManager mLinearLayoutManager;
    KnowledgeAdapter mAdapter;
    List<String> mKnowledgeList;

    @Override
    public void setUpComponent() {

    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("成长知识");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mKnowledgeList = new ArrayList<>();
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.selector);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new KnowledgeAdapter(this, mKnowledgeList);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mAdapter.setOnItemClickLitener(new KnowledgeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showShortToast("您点击了第"+position+"个内容");
                //startActivity(new Intent(this, NewsDetailActivity.class));
            }
        });
        ///mSwipeRefreshWidget.setRefreshing(true);
        mSwipeRefreshWidget.setOnRefreshListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_knowledge;
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

    @Override
    public void onRefresh() {
        mSwipeRefreshWidget.setRefreshing(false);
    }
}
