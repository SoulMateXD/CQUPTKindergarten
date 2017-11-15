package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.VideoListAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.bean.VideoCollectionBean;
import com.cqupt.kindergarten.bean.VideoListBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.ui.activity.HandbookVideoActivity;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

public class VideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.video_list_recycler)
    RecyclerView videoListRecycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;

    private static final String RESPONSE_NULL = "[]";
    private static final String URL = "http://119.29.225.57:8080/";
    private static final String COLLEGE_VIDEO_LIST_URL = URL+"kindergarden/MCShowAdmini";
    private static final String CLASS_VEDIO_LIST_URL = URL+"kindergarden/MCClassApp";
    private static final String COLLECTION_VIDEO_List_URL = URL + "kindergarden/CollectMoviecontent";

    private SharedPreferences sharedPreferences;

    private int userType;
    private int intentType;
    private int page = 1;
    private Map<String, Object> params;
    private String url;
    private String cID;

    private OkHttpUtil okHttpUtil;

    private ArrayList<VideoListBean> datas;
    private VideoListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_list;
    }

    @Override
    public void initView() {
        okHttpUtil = new OkHttpUtil(getActivity());
        getLocalValues();
        Bundle bundle = getArguments();
        intentType = bundle.getInt("type");

        datas = new ArrayList<>();
        adapter = new VideoListAdapter(getContext(), datas);
        adapter.setOnclick(new RecyclerOnclickInterface() {
            @Override
            public void onClick(View view, int position) {
                VideoListBean bean = datas.get(position);
                Intent intent = new Intent(getContext(), HandbookVideoActivity.class);
                intent.putExtra("VideoListData", bean);
                intent.putExtra("intentType", intentType);
                startActivity(intent);
            }
        });
        videoListRecycler.setAdapter(adapter);
        videoListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 52);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {

    }

    public static VideoListFragment newInstance(int intentType) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", intentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getLocalValues() {
        sharedPreferences = getContext().getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        userType = sharedPreferences.getInt("TYPE", 0);
        if (userType == PARENT) {
            Parent parent = DataSupport.findFirst(Parent.class);
            cID = parent.getcId();
        } else if (userType == TEACHER) {
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            cID = teacher.getcId();
        }
    }

    @Override
    public void onRefresh() {
        params = new HashMap<>();
        if (intentType == TYPE_CLASS) {
            url = CLASS_VEDIO_LIST_URL;
            params.put("mcJson", cID);
            params.put("pageNum", page);
        } else if (intentType == TYPE_NEWS) {
            url = COLLEGE_VIDEO_LIST_URL;
            params.put("pageNum", page);
        }else if (intentType == TYPE_COLLECTION){
            url = COLLECTION_VIDEO_List_URL;
            params.put("userid", userId);
        }
        page++;

        okHttpUtil.mOkHttpPost(url, params, new OkHttpUtil.OkHttpUtilCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (intentType == TYPE_COLLECTION){
                        VideoCollectionBean bean = GsonUtil.parseJsonWithGson(response, VideoCollectionBean.class);
                        if (page == 2){
                            VideoListBean listBean = new VideoListBean(bean.getCmname(), bean.getCmid());
                            datas.add(listBean);
                            adapter.notifyDataSetChanged();
                        }else{
                            ToastUtils.init(true);
                            ToastUtils.showShortToast("没有数据啦~");
                        }
                    }else {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = (JSONArray) jsonObject.get("tlist");
                        String value = jsonArray.toString();
                        if (value.equals(RESPONSE_NULL)){
                            ToastUtils.init(true);
                            ToastUtils.showShortToast("没有数据啦~");
                        }else {
                            ArrayList<VideoListBean> beans = GsonUtil.jsonToArrayList(value, VideoListBean.class);
                            datas.addAll(beans);
                            adapter.notifyDataSetChanged();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String response) {
                ToastUtils.showShortToast("数据加载失败");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
