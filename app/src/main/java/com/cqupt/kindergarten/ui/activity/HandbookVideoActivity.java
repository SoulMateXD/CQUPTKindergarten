package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.VideoListBean;
import com.cqupt.kindergarten.ui.fragment.HandBookVideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*   显示具体视频列表的Activity
* */
public class HandbookVideoActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.handbook_video_frame)
    FrameLayout handbookVideoFrame;

    private VideoListBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handbook_video);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        bean = getIntent().getParcelableExtra("VideoListData");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        HandBookVideoFragment fragment = HandBookVideoFragment.newInstance(bean);
        transaction.replace(R.id.handbook_video_frame, fragment);
        transaction.commit();
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
