package com.cqupt.kindergarten.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.String.valueOf;

/**
 * Created by SoulMateXD on 2017/6/13.
 */

public class OkHttpUtil {

    public static final int TIME_OUT = 0;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private Handler handler;
    private Callback okCallback;
    private Activity activity;

    /*
    * 由于将handler封装在了okHttpUtil中，所以创建handler时需要activity的looper，并让创建的handler处理该looper
    * */
    public OkHttpUtil(Activity activity){
        this.activity = activity;
    }

    /*
    * 初始化handler和okhttp的callback
    * */
    public void init(final Activity activity, final OkHttpUtilCallback callback){
        handler = new Handler(activity.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case SUCCESS:
                        String response = (String) msg.obj;
                        Log.d("OKHTTPUTIL", response);
                        callback.onSuccess(response);
                        break;
                    case FAILURE:
                        Exception exception = (Exception) msg.obj;
                        Toast.makeText(activity, "请求失败" + exception, Toast.LENGTH_SHORT).show();
                        callback.onFailure(exception.toString());
                        break;
                }
            }
        };

        okCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = FAILURE;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = SUCCESS;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        };

    }

    /*
    *  用于封装handler时用的callback
    * */
    public interface OkHttpUtilCallback {
        void onSuccess(String response);
        void onFailure(String response);
    }

    /*
    * 此处的callback用于执行请求成功或失败后的代码
    * */
    public void mOkHttpPost(String url, ArrayList<String> keys, ArrayList<String> values, OkHttpUtilCallback callback){
        init(activity, callback);
        FormBody.Builder formBody = new FormBody.Builder();
        int i = 0;
        for (; i<keys.size(); i++){
            formBody.add(keys.get(i), values.get(i));
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(okCallback);
    }

    public void mOkHttpPost(final String url, final Map<String, Object> map, OkHttpUtilCallback callback) {
        init(activity, callback);
        FormBody.Builder formBody = new FormBody.Builder();
        if (map != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : map.entrySet()) {
                formBody.add(valueOf(entry.getKey()), valueOf(entry.getValue()));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(formBody.build())
                .build();
        // readTimeout("请求超时时间" , 时间单位);
        mOkHttpClient.newCall(request)
                .enqueue(okCallback);

    }
    public void mOkHttpGet(String getUrl, OkHttpUtilCallback callback){
        init(activity, callback);
        Request request = new Request.Builder()
                .url(getUrl)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(okCallback);
    }
}
