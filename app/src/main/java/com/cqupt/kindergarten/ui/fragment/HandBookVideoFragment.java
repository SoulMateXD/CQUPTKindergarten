package com.cqupt.kindergarten.ui.fragment;

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

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.VideoAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.bean.VideoBean;
import com.cqupt.kindergarten.bean.VideoListBean;
import com.cqupt.kindergarten.listener.VideoRecyclerOnclickListener;
import com.cqupt.kindergarten.ui.activity.AlbumDetailsActivity;
import com.cqupt.kindergarten.ui.activity.VideoDetailsActivity;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;

import org.litepal.crud.DataSupport;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by SoulMateXD on 2017/5/9.
 */

public class HandBookVideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.video_recycler)
    RecyclerView videoRecycler;
    Unbinder unbinder;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    private static final String URL = "http://119.29.225.57:8080/";
    private static final String VIDEO_URL = URL+"kindergarden/MovieShowApp";
    private static final String COLLECT_URL = URL + "kindergarden/CollectMovTotal";
    private static final String KEY_MCID = "MCid";
    private static final String KEY_PAGENUM = "pageNum";
    private static final String RESPONSE_NULL = "[]";
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_IMAGE = 3;

    private int intentType;
    private String mcId;
    private String url;
    private int userType;
    private int page = 1;
    private VideoListBean bean;
    private ArrayList<VideoBean> datas;
    private VideoAdapter adapter;
    private OkHttpUtil okHttpUtil;
    private Map<String, Object> params;

    @Override

    public int getLayoutId() {
        return R.layout.fragment_handbook_video;
    }

    @Override
    public void initView() {
        bean = getArguments().getParcelable("VideoListBean");
        intentType = getArguments().getInt("intentType");
        okHttpUtil = new OkHttpUtil(getActivity());
        datas = new ArrayList<>();
        adapter = new VideoAdapter(getContext(), datas);

        mcId = bean.getMcid();


        adapter.setOnClickListener(new VideoRecyclerOnclickListener() {
            @Override
            public void onVideoImageClicked(int position) {
                Intent intent = new Intent(getContext(), AlbumDetailsActivity.class);
                VideoBean bean = datas.get(position);
                intent.putExtra("VideoBean", bean);
                intent.putExtra("IntentType", TYPE_VIDEO);
                startActivity(intent);
            }

            @Override
            public void onVideoDianzanClicked(int position, boolean beforeState, boolean currentState) {
//                Toast.makeText(getContext(), "您为第" + position + "个点了赞！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCollectClicked(int position, boolean beforeState, boolean currentState) {
//                Toast.makeText(getContext(), "您为第" + position + "个点了收藏", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCommentClicked(int position) {

            }
        });
        videoRecycler.setAdapter(adapter);
        videoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefresh.setProgressViewOffset(false, 0, 52);
        swipeRefresh.setOnRefreshListener(this);

        onRefresh();
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {

    }

    public static HandBookVideoFragment newInstance(VideoListBean bean, int intentType) {
        HandBookVideoFragment fragment = new HandBookVideoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("VideoListBean", bean);
        bundle.putInt("intentType", intentType);
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

    @Override
    public void onRefresh() {
        if (intentType == 2){
            url = COLLECT_URL;
            params = new HashMap<>();
            params.put("mcid", mcId);
            params.put(KEY_PAGENUM, page);
            page++;
        }else {
            url = VIDEO_URL;
            params = new HashMap<>();
            params.put(KEY_MCID, mcId);
            params.put(KEY_PAGENUM, page);
            page++;
        }
        okHttpUtil.mOkHttpPost(url, params, new OkHttpUtil.OkHttpUtilCallback() {
            @Override
            public void onSuccess(String response) {
                if (response.equals(RESPONSE_NULL)){
                    ToastUtils.init(true);
                    ToastUtils.showShortToast("没有数据啦~");
                }else {
                    ArrayList<VideoBean> beans = GsonUtil.jsonToArrayList(response, VideoBean.class);
                    datas.addAll(beans);
                    adapter.notifyDataSetChanged();
                }
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(String response) {
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}
