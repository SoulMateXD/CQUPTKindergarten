package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.NewsListAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.NewsListBean;
import com.cqupt.kindergarten.injection.component.DaggerNewsListFragmentComponent;
import com.cqupt.kindergarten.injection.component.NewsListFragmentComponent;
import com.cqupt.kindergarten.injection.module.NewsListFragmentModule;
import com.cqupt.kindergarten.presenter.NewsListFragmentPresenter;
import com.cqupt.kindergarten.ui.activity.NewsDetailActivity;
import com.cqupt.kindergarten.ui.ui_interface.INewsListFragment;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.MyDialog;
import com.cqupt.kindergarten.util.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/1/15.
 */

public class NewsListFragment extends BaseFragment implements INewsListFragment, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NewsListFragmentPresenter mNewsListFragmentPresenter;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private static final String URL = "http://119.29.53.178:8080/kindergarden/NewsStateSreach3";
    private static final String RESPONSE_NULL = "[]";
    private static final String KEY_A = "A";
    private static final String KEY_B = "B";
    private static final String KEY_C = "C";
    private static final String KEY_D = "D";
    private static final String KEY_PAGENUM = "pageNum";


    private NewsListAdapter mAdapter;
    private ArrayList<NewsListBean> datas = new ArrayList<>();
    private int page = 1;


    private NewsListFragmentComponent mNewsListFragmentComponent;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyDialog.SUCCESS:
                    String response = (String) msg.obj;
                    dealJson(response);
                    break;
                case MyDialog.FAILURE:
                    Exception exception = (Exception) msg.obj;
                    Toast.makeText(getContext(), "请求失败" + exception, Toast.LENGTH_SHORT).show();
                    break;
            }
            mSwipeRefreshWidget.setRefreshing(false);
        }
    };

    public static NewsListFragment newInstance() {

        Bundle args = new Bundle();
        NewsListFragment mFragment = new NewsListFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    public void initView() {
        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.selector);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //避免recyclerview 的item乱换位置
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mAdapter = new NewsListAdapter(getContext(), datas);

        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mAdapter.setOnItemClickLitener(new NewsListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //ToastUtils.showShortToast("您点击了第"+position+"个内容");
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("NewsListItem", datas.get(position));
                startActivity(intent);
            }
        });
        ///mSwipeRefreshWidget.setRefreshing(true);
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
        if (mNewsListFragmentComponent == null) {
            mNewsListFragmentComponent = DaggerNewsListFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .newsListFragmentModule(new NewsListFragmentModule(this))
                    .build();
            mNewsListFragmentComponent.inject(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser){
////            onRefresh();
//        }else {
//
//        }
//    }

    private void dealJson(String response) {
        if (response.equals(RESPONSE_NULL)){
            ToastUtils.init(true);
            ToastUtils.showShortToast("没有数据啦~");
        }else {
            ArrayList<NewsListBean> beans = GsonUtil.jsonToArrayList(response, NewsListBean.class);
            datas.addAll(beans);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_A, "全部");
        map.put(KEY_B, null);
        map.put(KEY_C, null);
        map.put(KEY_D, null);
        map.put(KEY_PAGENUM, page);
        page++;
        HttpUtil.mOkHttpPost(URL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = MyDialog.FAILURE;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = MyDialog.SUCCESS;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
    }
}
