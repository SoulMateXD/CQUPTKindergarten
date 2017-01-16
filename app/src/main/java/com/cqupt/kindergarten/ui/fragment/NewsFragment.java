package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerNewsFragmentComponent;
import com.cqupt.kindergarten.injection.component.NewsFragmentComponent;
import com.cqupt.kindergarten.injection.module.NewsFragmentModule;
import com.cqupt.kindergarten.presenter.NewsFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.INewsFragmentInterface;
import com.cqupt.kindergarten.util.GlideImageLoader;
import com.cqupt.kindergarten.util.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhx on 2017/1/15.
 */

public class NewsFragment extends BaseFragment implements INewsFragmentInterface, OnBannerClickListener {
    @Inject
    NewsFragmentPresenter mNewsFragmentPresenter;
    @BindView(R.id.banner)
    Banner mBanner;
    List<String> images;
    @BindView(R.id.ll_news)
    LinearLayout llNews;
    @BindView(R.id.ll_knowledge)
    LinearLayout llKnowledge;

    private NewsFragmentComponent mNewsFragmentComponent;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        images = new ArrayList<>();
        images.add("http://qn.img.ibabyzone.cn/upload/image/2017/01/13/1484293599113489.jpg");
        images.add("http://qn.img.ibabyzone.cn/upload/image/2017/01/12/1484202371751248.jpg");
        images.add("http://qn.img.ibabyzone.cn/upload/image/2017/01/11/1484123240518194.jpg");
        mBanner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerClickListener(this)
                .start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.ll_news, R.id.ll_knowledge})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_news:

                break;
            case R.id.ll_knowledge:
                break;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showShortToast("您点击了第"+position+"张图片");
    }

    @Override
    public void getBanner(List<String> imgurls) {

    }


    @Override
    public void setUpComponent() {
        if (mNewsFragmentComponent == null) {
            mNewsFragmentComponent = DaggerNewsFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsFragmentModule(new NewsFragmentModule(this))
                    .build();
            mNewsFragmentComponent.inject(this);
        }
    }
}
