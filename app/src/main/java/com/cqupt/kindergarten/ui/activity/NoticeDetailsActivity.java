package com.cqupt.kindergarten.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.bean.NoticeListBean;
import com.cqupt.kindergarten.util.ScreenUtils;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.ImageFixCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.activity_news_toolbar)
    Toolbar activityNewsToolbar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_publisher)
    TextView tvPublisher;

    private String title;
    private String message;
    private NoticeListBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(activityNewsToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        bean = intent.getParcelableExtra("NoticeItem");

        toolbarTitle.setText(bean.getTitle());
        if (bean.getMessage() != null) {
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
                    .into(tvContent);
            tvPublisher.setText("发布人: "+bean.getIssuer());
        }

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
