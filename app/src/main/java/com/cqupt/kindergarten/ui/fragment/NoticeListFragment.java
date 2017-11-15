package com.cqupt.kindergarten.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.NoticeListAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.NewsListBean;
import com.cqupt.kindergarten.bean.NoticeListBean;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.injection.component.DaggerNoticeListFragmentComponent;
import com.cqupt.kindergarten.injection.component.NoticeListFragmentComponent;
import com.cqupt.kindergarten.injection.module.NoticeListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.activity.NoticeDetailsActivity;
import com.cqupt.kindergarten.ui.ui_interface.INewsListFragment;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/1/15.
 */

public class NoticeListFragment extends BaseFragment implements INewsListFragment, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NewsListFragmentPresenter mNewsListFragmentPresenter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    LinearLayoutManager mLinearLayoutManager;
    NoticeListAdapter mAdapter;
    List<NoticeListBean> notices;

    private static final String URL = "http://119.29.225.57:8080/";
    private static final String URL_COLLEGE = URL+"kindergarden/showPumGg";
    private static final String URL_CLASS = URL+"kindergarden/showclassGg";
    private static final String URL_SUIBI = URL + "kindergarden/ClassEduction";
    private static final String RESPONSE_NULL = "[]";
    private static final String KEY_PAGENUM = "pagenum";
    private static final String KEY_CID = "cid";
    /*
    *  class，和 news 两个fragment中，公告和图鉴模块，用于跳转判断
    * */
    public static int TYPE_CLASS = 0;
    public static int TYPE_NEWS = 1;

    private SharedPreferences sharedPreferences;

    private int userType;

    private int intentType;

    private OkHttpUtil okHttpUtil;
    private int page = 1;
    private String cid;
    private String url;

    private NoticeListFragmentComponent mNoticeListFragmentComponent;

    public static NoticeListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("IntentType", type);
        NoticeListFragment mFragment = new NoticeListFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    public void initView() {
        getLocalValues();
        intentType = getArguments().getInt("IntentType");
        okHttpUtil = new OkHttpUtil((Activity) getContext());
        notices = new ArrayList<>();
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.selector);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new NoticeListAdapter(getContext(), notices);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mAdapter.setOnItemClickLitener(new NoticeListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), NoticeDetailsActivity.class);
                intent.putExtra("NoticeItem", notices.get(position));
                startActivity(intent);
            }
        });
        mSwipeRefreshWidget.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void initData() {

    }


    @Override
    public void getNews() {

    }

    @Override
    public void setUpComponent() {
        if (mNoticeListFragmentComponent == null) {
            mNoticeListFragmentComponent = DaggerNoticeListFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .noticeListFragmentModule(new NoticeListFragmentModule(this))
                    .build();
            mNoticeListFragmentComponent.inject(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_PAGENUM, page);
        page++;
        url = URL_COLLEGE;
        if (intentType == TYPE_CLASS) {
            map.put(KEY_CID, cid);
            url = URL_CLASS;
        }

        if (intentType == 3){
            map = new HashMap<>();
            map.put("pageNum", page);
            map.put("cid", cid);
            url = URL_SUIBI;
        }

        okHttpUtil.mOkHttpPost(url, map, new OkHttpUtil.OkHttpUtilCallback() {
            @Override
            public void onSuccess(String response) {
                dealJson(response);
                mSwipeRefreshWidget.setRefreshing(false);
            }

            @Override
            public void onFailure(String response) {
                Toast.makeText(getContext(), "请求失败" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dealJson(String response) {
        if (response.equals(RESPONSE_NULL )||response.equals("{\"false\":\"false\"}")){
            ToastUtils.init(true);
            ToastUtils.showShortToast("没有数据啦~");
        }else {
            List<NoticeListBean> beans = GsonUtil.jsonToArrayList(response, NoticeListBean.class);
            notices.addAll(beans);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getLocalValues() {
        sharedPreferences = getContext().getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        userType = sharedPreferences.getInt("TYPE", 0);
        if (userType == PARENT) {
            Parent parent = DataSupport.findFirst(Parent.class);
            cid = parent.getcId();
        } else if (userType == TEACHER) {
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            cid = teacher.getcId();
        }
    }
}
