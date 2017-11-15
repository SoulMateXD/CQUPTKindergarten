package com.cqupt.kindergarten.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseActivity;
import com.cqupt.kindergarten.bean.LoginMessageParent;
import com.cqupt.kindergarten.bean.LoginMessageTeacher;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.util.GsonUtil;
import com.cqupt.kindergarten.util.HttpUtil;
import com.cqupt.kindergarten.util.LogUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.http.Url;

import static android.R.id.message;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_username_wrapper)
    TextInputLayout loginUsernameWrapper;
    @BindView(R.id.login_password_wrapper)
    TextInputLayout loginPasswordWrapper;
    @BindView(R.id.login_button)
    TextView loginButton;


    private static final String PARENT_LOGIN_SUCCESS = "家长登录成功";
    private static final String USER_EMPTY = "用户为空";
    private static final String PASSWORD_ERROR = "密码错误";
    private static final String TEACHER_LOGIN_SUCCESS = "老师登录成功";
    private static final String TIME_OUT = "连接超时";
    private static final int TEACHER = 0;
    private static final int PARENT = 1;
    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final String IS_LOGIN = "isLogin";
    private static final String URL = "http://119.29.225.57:8080/";
    private static final String KGURL = URL+"kindergarden/LoginServletApp";

    private String userName;
    private String passWord;
    private ProgressDialog progressDialog;
    private SharedPreferences preferences;

    private LogUtil log = new LogUtil("LoginActivity");

    @Override
    public void setUpComponent() {

    }

    @Override
    public void initView() {
        loadLoginDataFromPreferences();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login_button})
    public void onClick(View view) {
        userName = loginUsernameWrapper.getEditText().getText().toString();
        passWord = loginPasswordWrapper.getEditText().getText().toString();
        if (userName == null || userName.equals("")) {
            loginUsernameWrapper.setError("userName 不能为空");
            return;
        }else {
            loginUsernameWrapper.setError(null);
        }
        if (passWord == null | passWord.equals("")) {
            loginPasswordWrapper.setError("passWord 不能为空");
            return;
        }else {
            loginPasswordWrapper.setError(null);
        }

        switch (view.getId()) {
            case R.id.login_button:
                hideKeyBoard();
                login();
                break;
        }
    }

    private void loadLoginDataFromPreferences() {
        preferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        boolean isLogin;
        isLogin = preferences.getBoolean(IS_LOGIN, false);
        int type = preferences.getInt("TYPE", 0);
        if (isLogin){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("TYPE", type);
            startActivity(intent);
            finish();
        }
    }

    private void saveParentDataToPreferences(LoginMessageParent loginMessageParent){
        preferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt("TYPE", PARENT);
        editor.putString("Appid", loginMessageParent.getAppid());
        editor.putString("Apppassword", passWord);
        editor.apply();
        log.d(loginMessageParent.toString());

        loginMessageParent.save();

        setType(PARENT);//设置BaseActivity中的type，方便之后的activity获取

        boolean ii = loginMessageParent.save();
        log.d(ii + "");
        Parent parent = loginMessageParent.getObject();
        parent.save();
    }

    private void saveTeacherDataToPreferences(LoginMessageTeacher loginMessageTeacher){
        preferences = getSharedPreferences(LOGIN_SHARED_PREFRERNCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt("TYPE", TEACHER);
        editor.putString("Apppassword", passWord);
        editor.putString("Appid", loginMessageTeacher.getAppid());
        editor.apply();
        log.d(loginMessageTeacher.toString());

        boolean ii = loginMessageTeacher.save();
        log.d(ii + "");
        Teacher teachers = loginMessageTeacher.getObject();
        Teacher teacher = teachers;
        teacher.save();

        setType(TEACHER);//设置BaseActivity中的type，方便之后的activity获取
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

    /*
    *   show，登录，转圈圈
    * */
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("请等待");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    if (progressDialog.isShowing()){
                        dismissProgressDialog();
                        Message message = new Message();
                        message.obj = TIME_OUT;
                        handler.sendMessage(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /*
    *   让ProgressDialog消失
    * */
    private void dismissProgressDialog(){
        progressDialog.dismiss();
    }


    /*
    *   登录方法
    *   网络请求获取jsonObject并传给handler判断
    * */
    private void login() {
        showProgressDialog();
        final HashMap<String, String> params = new HashMap<>();
        params.put("Appid", userName);
        params.put("Apppassword", passWord);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpUtil.post(KGURL, params);
                Message message = new Message();
                message.obj = response;
                handler.sendMessage(message);

            }
        }).start();
    }

    /*
    *   处理jsonObject，判断用户类型跳转，用saveDataToPreference保存用户基本信息
    * */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String response;
            try {
                JSONObject outer = new JSONObject((String) msg.obj);
                response = outer.getString("msg");
                switch (response){
                    case PARENT_LOGIN_SUCCESS:
                        loginPasswordWrapper.setError(null);
                        loginUsernameWrapper.setError(null);
                        LoginMessageParent loginMessageParent = GsonUtil.parseJsonWithGson(outer.toString(), LoginMessageParent.class);

                        saveParentDataToPreferences(loginMessageParent);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("TYPE", PARENT);
                        startActivity(intent);

                        finish();
                        break;
                    case TEACHER_LOGIN_SUCCESS:
                        loginPasswordWrapper.setError(null);
                        loginUsernameWrapper.setError(null);
                        LoginMessageTeacher loginMessageTeacher = GsonUtil.parseJsonWithGson(outer.toString(), LoginMessageTeacher.class);

                        saveTeacherDataToPreferences(loginMessageTeacher);

                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        intent1.putExtra("TYPE", TEACHER);
                        startActivity(intent1);

                        finish();
                        break;
                    case PASSWORD_ERROR:
                        loginPasswordWrapper.setError("密码错误");
                        break;
                    case USER_EMPTY:
                        loginUsernameWrapper.setError("用户名错误");
                        break;
                    case TIME_OUT:
                        Toast.makeText(getApplicationContext(), "您的网络有点不给力噢！请检查下网络设置^.^", Toast.LENGTH_SHORT).show();
                        break;
                }
                dismissProgressDialog();
            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "哎哟，服务器出错了！", Toast.LENGTH_SHORT).show();
            } catch(NullPointerException e){
                /*
                *   测试代码，用于服务器没有开启
                * */
                Toast.makeText(getApplicationContext(), "哎哟，服务器出错了！", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                intent1.putExtra("TYPE", TEACHER);
                startActivity(intent1);
            }
        }
    };
}


