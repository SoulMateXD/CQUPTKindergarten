package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.AlbumAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.AlbumAdapterBean;
import com.cqupt.kindergarten.bean.AlbumBeanFromJson;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.ui.activity.CollegeAlbumActivity;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.MyDialog;
import com.cqupt.kindergarten.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by SoulMateXD on 2017/5/9.
 */

public class HandBookAlbumFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.handbook_album_recycler)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private static final String URL_ALBUM = "http://119.29.53.178:8080/kindergarden/PCtShowteacher";
    private static final String KEY_PAGENUM = "pageNum";
    private static final String KEY_CID = "cid";
    private static final String RESPONSE_NULL = "[]";
    private static final String DEFAULT_IMAGE_URL = "http://imgsrc.baidu.com/forum/w%3D580/sign=79323d29054f78f0800b9afb49300a83/e32e6159252dd42aaaeba2d6053b5bb5c8eab8f5.jpg";
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;

    private int page = 1;
    private ArrayList<AlbumAdapterBean> datas;
    private AlbumAdapter adapter;
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
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    @Override
    public int getLayoutId() {
        return R.layout.fragment_handbook_album;
    }

    @Override
    public void initView() {
        datas = new ArrayList<>();
        adapter = new AlbumAdapter(getContext(), datas);
        adapter.setOnclick(new RecyclerOnclickInterface() {
            @Override
            public void onClick(View view, int position) {
                AlbumAdapterBean bean = datas.get(position);
                Intent intent = new Intent(getContext(), CollegeAlbumActivity.class);
                intent.putExtra("AlbumData", bean);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //避免recyclerview 的item乱换位置
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 52);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }

    //设置当fragment被用户看见时开始网络请求
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
////            onRefresh();
//        } else {
//
//        }
//    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {

    }

    public static HandBookAlbumFragment newInstance() {
        HandBookAlbumFragment fragment = new HandBookAlbumFragment();
        Bundle bundle = new Bundle();

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

    private void dealJson(String response) {
        if (response.equals(RESPONSE_NULL)){
            ToastUtils.init(true);
            ToastUtils.showShortToast("没有数据啦~");
        }else {
            ArrayList<AlbumBeanFromJson> beans = GsonUtil.jsonToArrayList(response, AlbumBeanFromJson.class);
//            ToastUtils.init(true);
//            ToastUtils.showLongToast(beans.toString());
            for (int i=0; i<beans.size(); i++){
                AlbumBeanFromJson bean = beans.get(i);
                String imageUrl = null;
                if(bean.getPicface() == null){
                    datas.add(new AlbumAdapterBean(DEFAULT_IMAGE_URL, "99张", bean.getPicname(), bean.getPicid()));
                    continue;
                }
                try {
                    JSONObject jsonObject = new JSONObject(bean.getPicface());
                    imageUrl = jsonObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (imageUrl!=null)
                    datas.add(new AlbumAdapterBean(imageUrl, "99张", bean.getPicname(), bean.getPicid()));
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_CID, "1");
        map.put(KEY_PAGENUM, page);
        page++;
        HttpUtil.mOkHttpPost(URL_ALBUM, map, new Callback() {
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
