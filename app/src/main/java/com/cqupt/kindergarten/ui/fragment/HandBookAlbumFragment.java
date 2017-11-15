package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.AlbumAdapter;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.AlbumAdapterBean;
import com.cqupt.kindergarten.bean.AlbumBeanFromJson;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.ui.activity.CollegeAlbumActivity;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.MyDialog;
import com.cqupt.kindergarten.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.net.URL;
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
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private static final String URL = "http://119.29.225.57:8080/";
    private static final String URL_COLLEGE_ALBUM = URL+"kindergarden/PCtShowteacher";
    private static final String URL_CLASS_ALBUM = URL+"kindergarden/PCShowApp";
    private static final String URL_COLLECTION_ALBUM = URL + "kindergarden/CollectPhotocontent";
    private static final String KEY_PAGENUM = "pageNum";
    private static final String KEY_CID = "cid";
    private static final String RESPONSE_NULL = "[]";
    private static final String DEFAULT_IMAGE_URL = "http://imgsrc.baidu.com/forum/w%3D580/sign=79323d29054f78f0800b9afb49300a83/e32e6159252dd42aaaeba2d6053b5bb5c8eab8f5.jpg";


    private SharedPreferences sharedPreferences;
    private String cID;

    private Map<String, Object> map;
    private String url;

    private int userType;
    private int intentType;
    private int page;
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
        getLocalValues();
        Bundle bundle = getArguments();
        intentType = bundle.getInt("type");
        page = 1;
        datas = new ArrayList<>();
        adapter = new AlbumAdapter(getContext(), datas);
        adapter.setOnclick(new RecyclerOnclickInterface() {
            @Override
            public void onClick(View view, int position) {
                AlbumAdapterBean bean = datas.get(position);
                Intent intent = new Intent(getContext(), CollegeAlbumActivity.class);
                intent.putExtra("AlbumData", bean);
                intent.putExtra("intentType", intentType);
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
    public void initData() {

    }

    @Override
    public void setUpComponent() {

    }

    public static HandBookAlbumFragment newInstance(int type) {
        HandBookAlbumFragment fragment = new HandBookAlbumFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
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
        if (intentType == TYPE_COLLECTION){
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.d("AAAA", jsonObject.getString("cpname"));
                if (page == 2){
                    AlbumAdapterBean bean = new AlbumAdapterBean(jsonObject.getString("cpface"), "99张",
                            jsonObject.getString("cpname"), jsonObject.getString("cpid"));
                    datas.add(bean);
                    adapter.notifyDataSetChanged();
                }else {
                    ToastUtils.init(true);
                    ToastUtils.showShortToast("没有数据啦~");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }else {
            if (response.equals(RESPONSE_NULL)){
                ToastUtils.init(true);
                ToastUtils.showShortToast("没有数据啦~");
            }else {
                ArrayList<AlbumBeanFromJson> beans = GsonUtil.jsonToArrayList(response, AlbumBeanFromJson.class);
//            ToastUtils.init(true);
//            ToastUtils.showLongToast(beans.toString());
                for (int i=0; i<beans.size(); i++){
                    AlbumBeanFromJson bean = beans.get(i);
//                String imageUrl = null;
                    if(bean.getPicface() == null){
                        datas.add(new AlbumAdapterBean(DEFAULT_IMAGE_URL, "99张", bean.getPicname(), bean.getPicid()));
                        continue;
                    }
//                try {
//                    JSONObject jsonObject = new JSONObject(bean.getPicface());
//                    imageUrl = jsonObject.getString("url");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                if (imageUrl!=null)
                    datas.add(new AlbumAdapterBean(bean.getPicface(), "99张", bean.getPicname(), bean.getPicid()));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onRefresh() {
        if (intentType == TYPE_CLASS) {
            map = new HashMap<>();
            url = URL_CLASS_ALBUM;
            map.put(KEY_CID, cID);
            map.put(KEY_PAGENUM, page);
            page++;

        } else if (intentType == TYPE_NEWS) {
            map = new HashMap<>();
            url = URL_COLLEGE_ALBUM;
            map.put(KEY_CID, "1");
            map.put(KEY_PAGENUM, page);
            page++;
        }else if (intentType == TYPE_COLLECTION){
            map = new HashMap<>();
            url = URL_COLLECTION_ALBUM;
            map.put("userid", userId);
            page++;
        }
        HttpUtil.mOkHttpPost(url, map, new Callback() {
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
