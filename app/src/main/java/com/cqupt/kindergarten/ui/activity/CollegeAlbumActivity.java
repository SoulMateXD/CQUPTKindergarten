package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.CollegeAlbumInnerAdapter;
import com.cqupt.kindergarten.bean.AlbumAdapterBean;
import com.cqupt.kindergarten.bean.CollegeAlbumBean;
import com.cqupt.kindergarten.bean.ImageItemBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CollegeAlbumActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.activity_college_album_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_college_album_recycler)
    RecyclerView recycler;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String KEY_PAGENUM = "pageNum";
    private static final String KEY_PID = "pid";
    private static final String URL_ALBUM_DETAIL = "http://172.20.2.164:8080/kindergarden/PictureShowApp";
    private static final String RESPONSE_NULL = "{\"result\":false}";
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_IMAGE = 3;

    private String AlbumId;
    private ArrayList<CollegeAlbumBean> datas = new ArrayList<>();
    private int page = 1;

    private CollegeAlbumAdapter adapter;
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
                    Toast.makeText(CollegeAlbumActivity.this, "请求失败" + exception, Toast.LENGTH_SHORT).show();
                    break;
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_album);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        AlbumAdapterBean bean = intent.getParcelableExtra("AlbumData");
        AlbumId = bean.getAlbumId();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(bean.getAlbumTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new CollegeAlbumAdapter(this, datas);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light
                ,android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0, 52);
        swipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
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
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_PAGENUM, page);
        map.put(KEY_PID, AlbumId);
        page++;

        HttpUtil.mOkHttpPost(URL_ALBUM_DETAIL, map, new Callback() {
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

    private void dealJson(String response) {
        if (response.equals(RESPONSE_NULL)){
            ToastUtils.init(true);
            ToastUtils.showShortToast("没有数据啦~");
        }else {
            //这里，由于后台给的url数据有问题，是以json字符串做的值，要再次解析。。。
            ArrayList<CollegeAlbumBean> beans = GsonUtil.jsonToArrayList(response, CollegeAlbumBean.class);
            for (CollegeAlbumBean outerBean : beans){
                ArrayList<ImageItemBean> innerBeans = outerBean.getT();
                for (ImageItemBean innerBean : innerBeans){
                    //默认网上的图片
                    String url = "http://pic.service.yaolan.com/32/20160906/98/1473134188002_1_w600_h400_o.jpg";
                    try {
                        url = new JSONObject(innerBean.getXcAdress()).getString("url");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    innerBean.setXcAdress(url);
                }
            }
            datas.addAll(beans);
            adapter.notifyDataSetChanged();
        }
    }

    //相册详情页的adapter
    class CollegeAlbumAdapter extends RecyclerView.Adapter<ViewHolder> {
        private Context context;
        private ArrayList<CollegeAlbumBean> datas;


        CollegeAlbumAdapter(Context context, ArrayList<CollegeAlbumBean> datas) {
            this.datas = datas;
            this.context = context;
            initData();
        }

        private void initData() {
//            datas = new ArrayList<>();
//            ArrayList<String> imageUrls = new ArrayList<>();
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=79323d29054f78f0800b9afb49300a83/e32e6159252dd42aaaeba2d6053b5bb5c8eab8f5.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=1ed91d5e652762d0803ea4b790ec0849/7bc6d4628535e5dd502407a270c6a7efce1b6243.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=a52feb948b1001e94e3c1407880f7b06/3af01ad5ad6eddc4573916863fdbb6fd536633f0.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=4096b56edb54564ee565e43183df9cde/dfc0b64543a98226c63ea25a8c82b9014b90ebd0.jpg");
//            imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495170321955&di=73bb6019b3cb0cd9056613865335cb38&imgtype=0&src=http%3A%2F%2Fp.3761.com%2Fpic%2F5201401504971.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=807ff303231f95caa6f592bef9167fc5/bfd0b21c8701a18b90a3e432982f07082838fe03.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=5b9f7856b651f819f1250342eab54a76/86159213b07eca8007b5c79a972397dda144831b.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=b796e1e558df8db1bc2e7c6c3922dddb/c332aec379310a5558ef3e89b14543a98326108c.jpg");
//            imageUrls.add("http://imgsrc.baidu.com/forum/w%3D580/sign=577315755db5c9ea62f303ebe538b622/b68fa71ea8d3fd1f22dc8144364e251f94ca5ffe.jpg");
//
//
//            datas.add(new CollegeAlbumBean("05/18", imageUrls));
//            datas.add(new CollegeAlbumBean("02/11", imageUrls));
//            datas.add(new CollegeAlbumBean("03/28", imageUrls));
//            datas.add(new CollegeAlbumBean("08/11", imageUrls));
//            datas.add(new CollegeAlbumBean("07/20", imageUrls));
//            datas.add(new CollegeAlbumBean("01/21", imageUrls));
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_college_album_outer_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final CollegeAlbumBean bean = datas.get(position);
            holder.time.setText(bean.getTime());
            holder.recyclerView.setLayoutManager
                    (new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            //每个子item里，九宫格图片的adapter
            CollegeAlbumInnerAdapter adapter = new CollegeAlbumInnerAdapter(context, bean.getT());
            adapter.setOnclickListener(new RecyclerOnclickInterface() {
                //跳转至图片详情页
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(CollegeAlbumActivity.this, AlbumDetailsActivity.class);
                    intent.putExtra("ImageUrl", bean.getT().get(position));
                    intent.putExtra("IntentType", TYPE_IMAGE);
                    startActivity(intent);
                }
            });
            holder.recyclerView.setAdapter(adapter);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time;
        private RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.item_college_album_outer_list_time);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.item_college_album_outer_list_recycler);

        }
    }


}
