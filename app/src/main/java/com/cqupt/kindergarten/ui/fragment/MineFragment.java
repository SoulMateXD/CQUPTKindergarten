package com.cqupt.kindergarten.ui.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.LoginMessageParent;
import com.cqupt.kindergarten.bean.LoginMessageTeacher;
import com.cqupt.kindergarten.bean.NoticeListBean;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.injection.component.DaggerMineFragmentComponent;
import com.cqupt.kindergarten.injection.component.MineFragmentComponent;
import com.cqupt.kindergarten.injection.module.MineFragmentModule;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.activity.ClassTimeTableActivity;
import com.cqupt.kindergarten.ui.activity.HandbookActivity;
import com.cqupt.kindergarten.ui.activity.LoginActivity;
import com.cqupt.kindergarten.ui.activity.MainActivity;
import com.cqupt.kindergarten.ui.activity.NoticeDetailsActivity;
import com.cqupt.kindergarten.ui.ui_interface.IMineFragmentInterface;
import com.cqupt.kindergarten.util.OkHttpUtil;
import com.cqupt.kindergarten.util.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.cqupt.kindergarten.util.MyDialog.TIME_OUT;
import static java.lang.String.valueOf;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements IMineFragmentInterface {

    @Inject
    MineFragmentPresenter mMineFragmentPresenter;
    @BindView(R.id.ming_user_image)
    RoundedImageView mingUserImage;
    @BindView(R.id.mine_user_name)
    TextView mineUserName;
    @BindView(R.id.mine_user_appid)
    TextView mineUserAppid;
    @BindView(R.id.mine_user_class)
    TextView mineUserClass;
    @BindView(R.id.mine_my_collection)
    RelativeLayout mineMyCollection;
    @BindView(R.id.mine_exit)
    TextView mineExit;
    Unbinder unbinder;
    @BindView(R.id.mine_summary)
    RelativeLayout mineSummary;
    @BindView(R.id.mine_regulations)
    RelativeLayout mineRegulations;

    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final String IS_LOGIN = "isLogin";
    public static final int CHOOSE_PHOTO = 2;
    private static final int TEACHER = 0;
    private static final int PARENT = 1;
    private static final String URL = "http://119.29.225.57:8080/";
    private static final String UPLOAD_TEACHER_IMAGE_URL = URL+"kindergarden/TeacherUpFace ";
    private static final String UPLOAD_PARENT_IMAGE_URL = URL+"kindergarden/StudentFace";
    private static final String KEY_PARENT = "sid";
    private static final String KEY_TEACHER = "tid";
    public static int TYPE_COLLECTION = 2;

    //上传图片所用key
    private String uploadUrl;
    private String keyId;
    private String keyName;

    private String Appid;
    private int type;       //是教师还是家长
    private String userImageUrl;    //头像url
    private SharedPreferences sharedPreferences;
    private String imagePath;  //上传图片时，本地获取到的图片路径

    private MineFragmentComponent mMineFragmentComponent;
    private MainActivity mainActivity;
    private ProgressDialog progressDialog;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {
        if (mMineFragmentComponent == null) {
            mMineFragmentComponent = DaggerMineFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .mineFragmentModule(new MineFragmentModule(this))
                    .build();
            mMineFragmentComponent.inject(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        sharedPreferences = mainActivity.getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        Appid = sharedPreferences.getString("Appid", "");
        type = sharedPreferences.getInt("TYPE", 0);

        if (type == PARENT) {
            Parent parent = DataSupport.findFirst(Parent.class);
            userImageUrl = parent.getSface();
            uploadUrl = UPLOAD_PARENT_IMAGE_URL;
            keyId = parent.getsId();
            keyName = KEY_PARENT;
            mineUserClass.setText("所在班级 : " + parent.getcId());
            mineUserName.setText("昵称 : " + parent.getsName());
            mineUserAppid.setText("账号 : " + Appid);
            Glide.with(getContext())
                    .load(userImageUrl)
                    .skipMemoryCache(true)
                    .into(mingUserImage);
        } else if (type == TEACHER) {
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            userImageUrl = teacher.getTface();
            uploadUrl = UPLOAD_TEACHER_IMAGE_URL;
            keyId = teacher.gettId();
            keyName = KEY_TEACHER;
            mineUserClass.setText("任课班级 : " + teacher.getcId());
            mineUserName.setText("昵称 : " + teacher.gettName());
            mineUserAppid.setText("账号 : " + Appid);
            Glide.with(getContext())
                    .load(userImageUrl)
                    .skipMemoryCache(true)
                    .into(mingUserImage);
        }

        userId = keyId;

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ming_user_image, R.id.mine_exit, R.id.mine_summary, R.id.mine_regulations, R.id.mine_my_collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ming_user_image:
                chooseFromAlbum();
                break;
            case R.id.mine_exit:
                exit();
                break;
            case R.id.mine_summary:
                NoticeListBean bean1 = new NoticeListBean("重邮幼儿园简介", "<h2 style=\"text-align: center;\"><b id=\"linkMyTitle\"></b></h2><p>重庆邮电大学幼儿园是重庆邮电大学举办，经教委批准的日托幼儿园，目前有5个教学班的规模，拥有24名高素质教职员工。</p><p>工作中，幼儿园努力践行科学发展观，积极依据教育部《幼儿园教育指导纲要》《3——6儿童学习与发展指南》实施幼儿素质教育，本着求真务实、科学发展的思路，以法规为准则，以规范为先导，以改革求发展，以科研创品牌，积极服务幼儿、服务家长、服务社会，构建以人为本的学习型、创新型、节约型和谐园所。</p><p>幼儿园于2004年评为重庆市一级幼儿园，荣获重庆市及南岸区“幼教先进集体”称号，教研团队被评为重庆市优秀教研组；曾参与中国体育科学会重点课题、中央教科所李忠忱教学法课题、市级幼儿早期阅读课题及校级心理健康课题研究，期间被评为优秀实验园，并荣获科研成果一等奖等奖项。目前正参加亲子趣味剪纸、细节化研讨等课题研究。</p><p>近5年来荣获多个奖项，其中国家级奖10余项、市级奖30余项，区级奖50余项，发表论文20余篇。同时作为本区唯一的幼儿园代表参加了南岸区中小学科技节师幼科幻绘画、剪纸制作展示等活动，深受小朋友的热爱，得到了家长、社会及领导的高度赞誉。</p><p>在今后的工作中，将继续本着务实求真、科学发展的理念，以更优质的幼儿教育回报家长、回报社会。</p>");
                Intent intent1 = new Intent(getContext(), NoticeDetailsActivity.class);
                intent1.putExtra("NoticeItem", bean1);
                startActivity(intent1);
                break;
            case R.id.mine_regulations:
                NoticeListBean bean2 = new NoticeListBean("重邮幼儿园办园章程",
                        "<h2 style=\"text-align: center;\"><b id=\"linkMyTitle\"></b></h2><p>办园目标：深化内涵，立足质量，创学习型、节约型和谐园所</p><p>办园理念：规范管理提效率，提升质量求生存，彰显特色促发展，优质服务创品牌</p><p>办园特色：创科艺结合教育特色</p><p>培养目标：快乐自信 &nbsp;富有爱心 &nbsp;亲善合作 &nbsp;享有智慧</p><p>园训:爱心献孩子 &nbsp;&nbsp;放心给家长 &nbsp;与时代同步 &nbsp;树幼教典范</p><p>园风：团结 &nbsp;进取 &nbsp;求实 &nbsp;创新</p><p>教风：自信 &nbsp;勤学 &nbsp;乐思 &nbsp;进取</p>");
                Intent intent2 = new Intent(getContext(), NoticeDetailsActivity.class);
                intent2.putExtra("NoticeItem", bean2);
                startActivity(intent2);
                break;
            case R.id.mine_my_collection:
                Intent collectionIntent = new Intent(getContext(), HandbookActivity.class);
                collectionIntent.putExtra("intentType", TYPE_COLLECTION);
                startActivity(collectionIntent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == -1 && data != null) {
                    Uri imageUri = data.getData();
                    imagePath = getImagePath(imageUri, null);
                    if (imagePath != null){
                        showProgressDialog();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File uploadFile = new File(imagePath);
                                uploadMultiFile(uploadFile, uploadUrl, keyName, keyId, new Callback() {
                                    @Override
                                    public void onFailure(Call call, final IOException e) {
                                        mainActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtils.showShortToast("图片上传失败");
                                                dismissProgressDialog();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {
                                        mainActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                String newUrl = null;
                                                try {
                                                    newUrl = response.body().string();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                ToastUtils.showShortToast(newUrl);
                                                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                                                mingUserImage.setImageBitmap(bitmap);

                                                if (type == PARENT) {
                                                    Parent parent = DataSupport.findFirst(Parent.class);
                                                    parent.setSface(newUrl);
                                                    parent.save();
                                                } else if (type == TEACHER) {
                                                    Teacher teacher = DataSupport.findFirst(Teacher.class);
                                                    teacher.setTface(newUrl);
                                                    teacher.save();
                                                }
                                                dismissProgressDialog();
                                            }
                                        });
                                    }
                                });
                            }
                        }).start();
                    }
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(mainActivity, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /*
    *   退出删除登录信息
    * */
    private void exit() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, false);
        editor.apply();

        if (type == PARENT) {
            DataSupport.deleteAll(LoginMessageParent.class);
            DataSupport.deleteAll(Parent.class);
        } else if (type == TEACHER) {
            DataSupport.deleteAll(LoginMessageTeacher.class);
            DataSupport.deleteAll(Teacher.class);
        }

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        mainActivity.finish();
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = mainActivity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void chooseFromAlbum() {
        if (ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }

    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }


//    /*
//    *   上传图片所用方法
//    * */
//    protected void post_file(final String url, final Map<String, Object> map, File file, Callback callback) {
//        OkHttpClient client = new OkHttpClient();
//        // form 表单形式上传
//        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        if(file != null){
//            // MediaType.parse() 里面是上传的文件类型。
//            RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
//            String filename = file.getName();
//            // 参数分别为， 请求key ，文件名称 ， RequestBody
//            requestBody.addFormDataPart("headImage", file.getName(), body);
//        }
//        if (map != null) {
//            // map 里面是请求中所需要的 key 和 value
//            for (Map.Entry entry : map.entrySet()) {
//                requestBody.addFormDataPart(valueOf(entry.getKey()), valueOf(entry.getValue()));
//            }
//        }
//        Request request = new Request.Builder().url(url).post(requestBody.build()).tag(this).build();
//        // readTimeout("请求超时时间" , 时间单位);
//        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).enqueue(callback);
//
//    }

    /*
   *   上传带参数的文件到服务器
   * */
    private void uploadMultiFile(File uploadFile, String uploadUrl, String keyName, String keyId, Callback callback){
//        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
//        multipartBodyBuilder.setType(MultipartBody.FORM);
//        multipartBodyBuilder.addFormDataPart("tid", "123");
//        multipartBodyBuilder.addFormDataPart("image", uploadFile.getName(), RequestBody.create( MediaType.parse("image/png"), uploadFile));
//        RequestBody requestBody = multipartBodyBuilder.build();
//        Request request = new Request.Builder().url(uploadUrl).post(requestBody).build();
        RequestBody fileBody = RequestBody.create
                (MediaType.parse("image/png"), uploadFile);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //写入 key  value  参数
                //写入 文件
                //参数必须自己拼接

//                .addFormDataPart("image", uploadFile.getName(), fileBody)
                .addPart(Headers.of(
                        "Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\"")
                        , fileBody)
//                .addPart(Headers.of(
//                        "Content-Disposition",
//                        "form-data; name=\"tid\"")
//                        ,RequestBody.create(null, "3"))
                .addFormDataPart("tid", keyId)
                .build();
        Request request = new Request.Builder()
                .url(uploadUrl + "?"+keyName+"=" + keyId)
                .header("Content-type", "multipart/form-data")
                .post(requestBody)
                .build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient =
                builder.connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build();
        okHttpClient.newCall(request).enqueue(callback);

    }

    /*
    *   请求数据，转圈圈
    * */
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle("请稍后 ^.^");
        progressDialog.setMessage("上传照片中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(15000);
                    if (progressDialog.isShowing()) {
                        Message message = new Message();
                        message.what = TIME_OUT;
                        handler.sendMessage(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int response = msg.what;
            switch (response) {
                case TIME_OUT:
                    Toast.makeText(getContext(), "您的网络有点不给力噢！请检查下网络设置^.^", Toast.LENGTH_SHORT).show();
                    break;

            }
            dismissProgressDialog();
        }
    };

    /*
    *   让ProgressDialog消失
    * */
    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }
}
