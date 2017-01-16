package com.cqupt.kindergarten.presenter;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.cqupt.kindergarten.base.BasePresenter;
import com.cqupt.kindergarten.ui.ui_interface.ILoginFragmentInterface;
import com.cqupt.kindergarten.util.ToastUtils;

/**
 * Created by inferno on 2017/1/14.
 */

public class LoginFragmentPresenter extends BasePresenter<ILoginFragmentInterface>{

    private static final String FRAGMENT_REPLACE = "com.cqupt.kindergarten.registerFragment.replace";
    private static final String MINE_FRAGMENT_REPLACE = "com.cqupt.kindergarten.MineFragment.replace";

    //    public LoginFragmentPresenter(DataManager dataManager){
    //    }
    public LoginFragmentPresenter(){
    }

    /**
     * 通知Activity动态替换Fragment,使用广播
     */
    public void notifyMainActivityReplace(Context context){

        Intent intent = new Intent(FRAGMENT_REPLACE);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        System.out.println("发送成功");
    }

    public void login(Context context, String etUsername, String etPassword){
        if(TextUtils.isEmpty(etUsername) || TextUtils.isEmpty(etPassword)){
            ToastUtils.showShortToast("账号或者密码不能为空");
        } else{
            //提交账号密码判断成功
            Intent intent = new Intent(MINE_FRAGMENT_REPLACE);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}
