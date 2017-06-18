package com.cqupt.kindergarten.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.cqupt.kindergarten.ui.activity.ClassTimeTableActivity;

/**
 * Created by SoulMateXD on 2017/5/21.
 */

public class MyDialog {

    public static final int TIME_OUT = 0;
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;


    private ProgressDialog progressDialog;
    private OnFailureCallback callback;


    /*
    *   请求数据，转圈圈
    * */
    public void showProgressDialog(final Context context){
        progressDialog = new ProgressDialog(context);
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
                        Toast.makeText(context, "您的网络有点不给力噢！请检查下网络设置^.^", Toast.LENGTH_SHORT).show();
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
    public void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    public interface OnFailureCallback{
        void onFailure();
    }
}
