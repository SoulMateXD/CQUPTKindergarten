package com.cqupt.kindergarten.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.adapter.ImageChatAdapter;
import com.cqupt.kindergarten.bean.ImageChatBean;
import com.cqupt.kindergarten.bean.ImageItemBean;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.bean.VideoBean;
import com.cqupt.kindergarten.listener.RecyclerOnclickInterface;
import com.cqupt.kindergarten.util.GlideImageDownloadUtil;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.MyDialog;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.view.View.GONE;

public class AlbumDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.album_details_image)
    ImageView image;
    @BindView(R.id.album_details_time_text)
    TextView timeText;
    @BindView(R.id.album_details_download_icon)
    ImageView messageIcon;
    @BindView(R.id.album_details_collect)
    ImageView collectIcon;
    @BindView(R.id.album_details_dianzan)
    ImageView dianzanIcon;
    @BindView(R.id.album_details_sth_layout)
    RelativeLayout sthLayout;
    @BindView(R.id.album_details_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.chat_text_input_edit)
    EditText inputEdit;
    @BindView(R.id.chat_button_send)
    TextView sendButton;
    @BindView(R.id.bottom_chat_layout)
    LinearLayout bottomChatLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.total_comment_text)
    TextView totalComment;
    @BindView(R.id.album_details_comment_icon)
    ImageView albumDetailsCommentIcon;
    @BindView(R.id.album_details_dianzan_number)
    TextView dianzanNumberText;
    @BindView(R.id.video_details_player)
    JCVideoPlayerStandard videoPlayer;

    @BindView(R.id.activity_main)
    CoordinatorLayout activityMain;

    private static final String URL_IMAGE_COMMENT = "http://172.20.2.164:8080/kindergarden/CommunicateShow";
    private static final String URL_IMAGE_ADD_COMMENT = "http://172.20.2.164:8080/kindergarden/CommunicateAdd";
    private static final String URL_IMAGE_ADD_LIKE = "http://172.20.2.164:8080/kindergarden/PictureLike";
    private static final String URL_VIDEO_COMMENT = "http://172.20.2.164:8080/kindergarden/CommunicateShow";
    private static final String URL_VIDEO_ADD_COMMENT = "http://172.20.2.164:8080/kindergarden/CommunicateAdd";
    private static final String URL_VIDEO_ADD_LIKE = "http://172.20.2.164:8080/kindergarden/MovieLike";

    private static final String KEY_COMID = "ComId";
    private static final String KEY_XID = "XId";
    private static final String KEY_ADD_COMMENT = "CommuniJson";
    private static final String SAY_STH = "说点什么吧...  ";
    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final int TEACHER = 0;
    private static final int PARENT = 1;
    private static final int TYPE_VIDEO = 2;
    private static final int TYPE_IMAGE = 3;


    private boolean isDianzan;
    private boolean isCollect;
    private ImageItemBean imageBean;
    private VideoBean videoBean;
    private int intentType;
    private String likeUrl;
    private String commentUrl;
    private String addCommentUrl;
    private String imageUrl;
    private String videoUrl;
    private String xId;
    private int dianzanNumber;
    private ArrayList<ImageChatBean> datas = new ArrayList<>();
    private ImageChatAdapter adapter;
    private String userName;
    private ImageChatBean chatBean = null; // 用于区别点击回复事件和普通回复事件
    private boolean isResponse;
    private OkHttpUtil okHttpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        okHttpUtil = new OkHttpUtil(this);
        getUserImformation();
        Intent intent = getIntent();
        intentType = intent.getIntExtra("IntentType", 0);
        if (intentType == 0) {
            ToastUtils.showShortToast("出错了。。");
        } else if (intentType == TYPE_IMAGE) {
            videoPlayer.setVisibility(GONE);
            image.setVisibility(View.VISIBLE);
            imageBean = intent.getParcelableExtra("ImageUrl");
            imageUrl = imageBean.getXcAdress();
            xId = imageBean.getXcId();
            dianzanNumber = Integer.valueOf(imageBean.getpLike());
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.default_image)
                    .into(image);
            timeText.setText(imageBean.getXcDate());
            dianzanNumberText.setText(dianzanNumber + "");

            commentUrl = URL_IMAGE_COMMENT;
            addCommentUrl = URL_IMAGE_ADD_COMMENT;
            likeUrl = URL_IMAGE_ADD_LIKE;
            toolbarTitle.setText("图片详情");
        } else if (intentType == TYPE_VIDEO) {
            videoPlayer.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            videoBean = intent.getParcelableExtra("VideoBean");
            videoUrl = videoBean.getMvAdress();
            xId = videoBean.getMvId();
            dianzanNumber = Integer.valueOf(videoBean.getMvLike());
            timeText.setText(videoBean.getMvDate());
            dianzanNumberText.setText(dianzanNumber + "");
            videoPlayer.widthRatio = 4;
            videoPlayer.heightRatio = 3;
            videoPlayer.setUp(videoUrl,
                    JCVideoPlayer.NORMAL_ORIENTATION, "");

            commentUrl = URL_VIDEO_COMMENT;
            addCommentUrl = URL_VIDEO_ADD_COMMENT;
            likeUrl = URL_VIDEO_ADD_LIKE;
            toolbarTitle.setText("视频详情");
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        bottomChatLayout.setVisibility(GONE);

        adapter = new ImageChatAdapter(this, datas);
        adapter.setOnClick(new RecyclerOnclickInterface() {
            @Override
            public void onClick(View view, int position) {
                sendResponseCommend(position);
            }
        });
        recyclerComment.setLayoutManager(new LinearLayoutManager(this));
        recyclerComment.setAdapter(adapter);
        recyclerComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard();
                bottomChatLayout.setVisibility(GONE);
                return false;
            }
        });

        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_green_light
                , android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefresh.setProgressViewOffset(false, 0, 52);
        swipeRefresh.setOnRefreshListener(this);
        onRefresh();


    }

    private void getUserImformation() {
        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        String Appid = sharedPreferences.getString("Appid", "");
        int type = sharedPreferences.getInt("TYPE", 0);

        if (type == PARENT) {
            Parent parent = DataSupport.findFirst(Parent.class);
            userName = parent.getsName();
        } else if (type == TEACHER) {
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            userName = teacher.gettName();
        }
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

    @OnClick({R.id.album_details_image, R.id.album_details_collect, R.id.album_details_dianzan, R.id.album_details_download_icon, R.id.album_details_comment_icon, R.id.chat_button_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.album_details_image:
                //点击显示大图
                Intent intent = new Intent(this, ImageDetailsActivity.class);
                intent.putExtra("ImageUrl", imageUrl);
                startActivity(intent);
                break;
            case R.id.album_details_collect:
                //收藏按钮
                isCollect = !isCollect;
                collectSwitch(collectIcon, isCollect);
                break;
            case R.id.album_details_dianzan:
                //点赞按钮
                isDianzan = !isDianzan;
                Map<String, Object> map = new HashMap<>();
                map.put(KEY_COMID, xId);
                okHttpUtil.mOkHttpPost(likeUrl, map, new OkHttpUtil.OkHttpUtilCallback() {
                    @Override
                    public void onSuccess(String response) {
                        dianzanSwitch(dianzanIcon, isDianzan);
                        dianzanNumberText.setText((dianzanNumber + 1) + "");
                        dianzanIcon.setClickable(false);
                    }

                    @Override
                    public void onFailure(String response) {
                        ToastUtils.showShortToast("不好意思，点赞失败了");
                    }
                });

                break;
            case R.id.album_details_download_icon:
                if (intentType == TYPE_IMAGE){
                    GlideImageDownloadUtil download = new GlideImageDownloadUtil(this, "CQUPTKindergarten");
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
                    download.savePicture(fileName, imageUrl);
                }else if (intentType == TYPE_VIDEO){
                    ToastUtils.showShortToast("视频暂时不支持下载哦~");
                }
                break;
            case R.id.album_details_comment_icon:
                //评论按钮，点击显示底部评论模块
                inputEdit.setHint(SAY_STH);
                bottomChatLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.chat_button_send:
                if (isResponse) {
                    sendComment(chatBean);
                    isResponse = false;
                    chatBean = null;
                } else {
                    sendComment(null);
                }
                inputEdit.setText("");

                break;
        }
    }

    private void sendComment(ImageChatBean bean) {
        ImageChatBean sendBean;
        String inputString = inputEdit.getText().toString();
        if (bean == null) {  // 如果bean为空则为普通回复，否则为点击回复
            //发送评论按钮，发送前判断edittext中内容
            if (inputString == null || inputString.equals("")) {
                ToastUtils.showShortToast("不能发空消息哦~");
                return;
            }
            //给要发送的json对象设置参数
            sendBean = new ImageChatBean();
            sendBean.setComId(null);
            sendBean.setPoneId(userName);
            sendBean.setPtwoId(null);
            sendBean.setComContent(inputString);
            sendBean.setComcount(0);
            sendBean.setxId(xId);
            sendBean.setComTime(null);
        } else {
            if (inputString == null || inputString.equals("")) {
                ToastUtils.showShortToast("不能发空消息哦~");
                return;
            }
            sendBean = bean;
            sendBean.setComContent(inputString);  // content必须在点击button后才能设置，所以这句话跑到这里来了。。
        }
        Gson gson = new Gson();
        String jsonString = gson.toJson(sendBean);

        Map<String, Object> map = new HashMap<>();
        map.put(KEY_ADD_COMMENT, jsonString);
        HttpUtil.mOkHttpPost(addCommentUrl, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShortToast("哎呀！评论失败了，请检查下网络哦~");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onRefresh();
            }
        });
        bottomChatLayout.setVisibility(GONE);
        hideKeyBoard();//这里必须隐藏软键盘
    }

    private void sendResponseCommend(int position) {
        ImageChatBean bean = datas.get(position);
        if (userName.equals(bean.getPoneId())) {
            inputEdit.setHint(SAY_STH);
            bottomChatLayout.setVisibility(View.VISIBLE);
        } else {
            inputEdit.setHint("回复 " + bean.getPoneId() + " : ");
            bottomChatLayout.setVisibility(View.VISIBLE);
            isResponse = true;
            ImageChatBean chatBean = new ImageChatBean();
            chatBean.setComId(null);
            chatBean.setPoneId(userName);
            chatBean.setPtwoId(bean.getPoneId());
            chatBean.setComContent(null);  // 在sendButton的点击事件中设置
            chatBean.setComcount(0);
            chatBean.setxId(xId);
            chatBean.setComTime(null);
            this.chatBean = chatBean;
        }
    }

    private void dianzanSwitch(ImageView image, boolean currentState) {
        if (currentState)
            image.setImageResource(R.drawable.dianzan_clicked);
        else
            image.setImageResource(R.drawable.dianzan_unclicked);
    }

    private void collectSwitch(ImageView image, boolean currentState) {
        if (currentState)
            image.setImageResource(R.drawable.collect_clicked);
        else
            image.setImageResource(R.drawable.collect_unclicked);
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
                    Toast.makeText(AlbumDetailsActivity.this, "请求失败" + exception, Toast.LENGTH_SHORT).show();
                    break;
            }
            swipeRefresh.setRefreshing(false);
        }
    };

    private void dealJson(String response) {
        ArrayList<ImageChatBean> beans = GsonUtil.jsonToArrayList(response, ImageChatBean.class);
        datas.clear();
        datas.addAll(beans);
        adapter.notifyDataSetChanged();
        recyclerComment.smoothScrollToPosition(datas.size());
        int number = datas.size();
        totalComment.setText("全部回复 ( 共" + number + "条 )");
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_XID, xId);

        HttpUtil.mOkHttpPost(commentUrl, map, new Callback() {
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

    /*
    *   隐藏软键盘
    * */
    private void hideKeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
