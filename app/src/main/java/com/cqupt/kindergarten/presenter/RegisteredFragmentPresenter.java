package com.cqupt.kindergarten.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.cqupt.kindergarten.base.BasePresenter;
import com.cqupt.kindergarten.ui.fragment.RegisteredFragment;
import com.cqupt.kindergarten.ui.ui_interface.INewsFragmentInterface;
import com.cqupt.kindergarten.ui.ui_interface.IRegisteredInterface;
import com.cqupt.kindergarten.util.ToastUtils;

/**
 * Created by zhx on 2017/1/15.
 */

public class RegisteredFragmentPresenter extends BasePresenter<IRegisteredInterface>{

    private static final String FRAGMENT_BACK = "com.cqupt.kindergarten.LoginFragment.replace";

    public RegisteredFragmentPresenter(){
    }

    public void notifyMainActivityReplace(Context context){
        Intent intent = new Intent(FRAGMENT_BACK);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void registerJudgment(RegisteredFragment fragment, String name, String password, String passwordConfirm, String number, String verification){
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(passwordConfirm) || TextUtils.isEmpty(password) || TextUtils
                .isEmpty(number) || TextUtils.isEmpty(verification)){
            ToastUtils.showShortToast("请完整填写资料");

        } else{

            if(number.length() != 11){
                ToastUtils.showShortToast("号码长度错误");
            } else{
                if(password.equals(passwordConfirm)){
                    if(password.length() < 6){
                        ToastUtils.showShortToast("密码长度不得小于6位");
                    } else{
                        //测试
                        if(!verification.equals("1111")){
                            ToastUtils.showShortToast("验证码错误");
                        } else{
                            //以后加提交成功的判断
                            fragment.registerSuccess();
                        }
                    }
                } else{
                    ToastUtils.showShortToast("两次密码不一致");
                }
            }
        }
    }
}
