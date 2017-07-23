package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.NewsListBean;
import com.cqupt.kindergarten.injection.component.DaggerNewsFragmentComponent;
import com.cqupt.kindergarten.injection.component.NewsFragmentComponent;
import com.cqupt.kindergarten.injection.module.NewsFragmentModule;
import com.cqupt.kindergarten.presenter.NewsFragmentPresenter;
import com.cqupt.kindergarten.ui.activity.HandbookActivity;
import com.cqupt.kindergarten.ui.activity.KnowledgeActivity;
import com.cqupt.kindergarten.ui.activity.NewsActivity;
import com.cqupt.kindergarten.ui.activity.NewsDetailActivity;
import com.cqupt.kindergarten.ui.ui_interface.INewsFragmentInterface;
import com.cqupt.kindergarten.util.GlideImageLoader;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.ll_tujian)
    LinearLayout llTujian;

    private static final String URL = "http://172.20.2.164:8080/kindergarden/NewsStateSreach3";
    private static final String RESPONSE_NULL = "[]";
    private static final String KEY_A = "A";
    private static final String KEY_B = "B";
    private static final String KEY_C = "C";
    private static final String KEY_D = "D";
    private static final String KEY_PAGENUM = "pageNum";
    private ArrayList<NewsListBean> datas = new ArrayList<>();

    private NewsFragmentComponent mNewsFragmentComponent;

    //用于区分class和news两个fragment中图鉴和新闻模块的点击事件
    private int intentType = TYPE_NEWS;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_A, "全部");
        map.put(KEY_B, null);
        map.put(KEY_C, null);
        map.put(KEY_D, null);
        map.put(KEY_PAGENUM, 1);

        images = new ArrayList<>();

        OkHttpUtil okHttpUtil = new OkHttpUtil(getActivity());
        okHttpUtil.mOkHttpPost(URL, map, new OkHttpUtil.OkHttpUtilCallback() {
            @Override
            public void onSuccess(String response) {
                dealJson(response);
            }

            @Override
            public void onFailure(String response) {

            }
        });


        mBanner.setImageLoader(new GlideImageLoader())
                .setOnBannerClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.ll_news, R.id.ll_knowledge, R.id.ll_tujian})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_news:
                intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("intentType", intentType);
                break;
            case R.id.ll_knowledge:
                intent = new Intent(getActivity(), KnowledgeActivity.class);
                break;
            case R.id.ll_tujian:
                intent = new Intent(getActivity(), HandbookActivity.class);
                intent.putExtra("intentType", intentType);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("NewsListItem", datas.get(position-1));
        startActivity(intent);
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

    private void dealJson(String response) {
        if (response.equals(RESPONSE_NULL)){
            ToastUtils.init(true);
            ToastUtils.showShortToast("目前没有新闻数据哦~");
        }else {
            ArrayList<NewsListBean> beans = GsonUtil.jsonToArrayList(response, NewsListBean.class);
            datas.addAll(beans);
            int length = 3;
            if (beans.size()<3){
                length = beans.size();
            }
            for (int i=0; i<length; i++){
                images.add(beans.get(i).getUrl1());
            }
            mBanner.setImages(images)
                    .start();
        }
    }

}
