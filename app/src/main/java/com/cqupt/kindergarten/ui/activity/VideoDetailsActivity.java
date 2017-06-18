package com.cqupt.kindergarten.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqupt.kindergarten.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.video_details_player)
    JCVideoPlayerStandard videoPlayer;
    @BindView(R.id.video_details_time_text)
    TextView timeText;
    @BindView(R.id.video_details_collect)
    ImageView collectIcon;
    @BindView(R.id.video_details_dianzan)
    ImageView dianzanIcon;

    private boolean isDianzan;
    private boolean isCollect;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTitle.setText("视频详情");

        videoPlayer.widthRatio = 4;
        videoPlayer.heightRatio = 3;
        videoPlayer.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4",
                JCVideoPlayer.NORMAL_ORIENTATION, "这是标题");
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

    @OnClick({R.id.video_details_collect, R.id.video_details_dianzan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.video_details_collect:
                isCollect = !isCollect;
                collectSwitch(collectIcon, isCollect);
                break;
            case R.id.video_details_dianzan:
                isDianzan = !isDianzan;
                dianzanSwitch(dianzanIcon, isDianzan);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    private void dianzanSwitch(ImageView image, boolean currentState){
        if (currentState)
            image.setImageResource(R.drawable.dianzan_clicked);
        else
            image.setImageResource(R.drawable.dianzan_unclicked);
    }

    private void collectSwitch(ImageView image, boolean currentState){
        if (currentState)
            image.setImageResource(R.drawable.collect_clicked);
        else
            image.setImageResource(R.drawable.collect_unclicked);
    }
}
