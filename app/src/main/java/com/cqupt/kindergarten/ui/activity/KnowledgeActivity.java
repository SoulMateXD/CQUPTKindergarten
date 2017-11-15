package com.cqupt.kindergarten.ui.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.KnowledgeAdapter;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.bean.AlbumAdapterBean;
import com.cqupt.kindergarten.bean.AlbumBeanFromJson;
import com.cqupt.kindergarten.bean.KnowledgeBean;
import com.cqupt.kindergarten.ui.ui_interface.INewsDetailActivityInterface;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.MyDialog;
import com.cqupt.kindergarten.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class KnowledgeActivity extends BaseActivity implements INewsDetailActivityInterface, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private SharedPreferences preferences;


    private static final int TEACHER = 0;
    private static final int PARENT = 1;
    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final String IS_LOGIN = "isLogin";
    private static final String URL = "http://119.29.225.57:8080/";
    private static final String URL_KNOWLEDGE = URL+"kindergarden/ShowAllGrowth";
    private static final String KEY_PAGENUM = "pageNum";
    private static final String RESPONSE_NULL = "[]";

    private LinearLayoutManager mLinearLayoutManager;
    private KnowledgeAdapter mAdapter;
    private ArrayList<KnowledgeBean> datas = new ArrayList<>();
    private int type;
    private int page = 1;

    @Override
    public void setUpComponent() {

    }

    @Override
    public void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mSwipeRefreshWidget.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.selector);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new KnowledgeAdapter(this, datas);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mRecyclerview.setHasFixedSize(true);
        mAdapter.setOnItemClickLitener(new KnowledgeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                ToastUtils.showShortToast("您点击了第" + position + "个内容");
                KnowledgeBean bean = datas.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(bean.getUrl2()));
                startActivity(intent);
            }
        });
        ///mSwipeRefreshWidget.setRefreshing(true);
        mSwipeRefreshWidget.setOnRefreshListener(this);
        loadLoginDataFromPreferences();
        if (type == PARENT) {

        }else if (type == TEACHER){

        }
        onRefresh();
    }

    @Override
    public void initData() {
        type = getType();
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
        mSwipeRefreshWidget.setEnabled(true);
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_PAGENUM, page);
        page++;
        HttpUtil.mOkHttpPost(URL_KNOWLEDGE, map, new Callback() {
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
        mSwipeRefreshWidget.setRefreshing(false);
    }

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
                    Toast.makeText(KnowledgeActivity.this , "请求失败" + exception, Toast.LENGTH_SHORT).show();
                    break;
            }
            mSwipeRefreshWidget.setRefreshing(false);
        }
    };

    private void dealJson(String response) {
        if (response.equals(RESPONSE_NULL)) {
            ToastUtils.init(true);
            ToastUtils.showShortToast("没有数据啦~");
        } else {
            ArrayList<KnowledgeBean> beans = GsonUtil.jsonToArrayList(response, KnowledgeBean.class);
            datas.addAll(beans);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void loadLoginDataFromPreferences() {
        preferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        type = preferences.getInt("TYPE", 0);
    }
}
