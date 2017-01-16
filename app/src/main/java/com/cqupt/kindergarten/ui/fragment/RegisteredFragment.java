package com.cqupt.kindergarten.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.DaggerRegisteredFragmentComponent;
import com.cqupt.kindergarten.injection.component.RegisteredFragmentComponent;
import com.cqupt.kindergarten.injection.module.RegisteredFragmentModule;
import com.cqupt.kindergarten.presenter.RegisteredFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.IRegisteredInterface;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by inferno on 2017/1/15.
 */

public class RegisteredFragment extends BaseFragment implements IRegisteredInterface{
    @Inject
    RegisteredFragmentPresenter mRegisteredFragmentPresenter;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_nameText)
    TextInputLayout mEtNameText;
    @BindView(R.id.name)
    LinearLayout mName;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_usernameText)
    TextInputLayout mEtUsernameText;
    @BindView(R.id.phone)
    LinearLayout mPhone;
    @BindView(R.id.et_passwordComfire)
    EditText mEtPasswordComfire;
    @BindView(R.id.et_passwordComfireText)
    TextInputLayout mEtPasswordComfireText;
    @BindView(R.id.pass_comfire)
    LinearLayout mPassComfire;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_passwordText)
    TextInputLayout mEtPasswordText;
    @BindView(R.id.pass)
    LinearLayout mPass;
    @BindView(R.id.et_verification)
    EditText mEtVerification;
    @BindView(R.id.tv_send_verification)
    TextView mTvSendVerification;
    @BindView(R.id.verification)
    LinearLayout mVerification;
    @BindView(R.id.btn_registered)
    Button mBtnRegistered;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;

    private RegisteredFragmentComponent mRegisteredFragmentComponent;
    private Timer mTimer;
    private int second = 5;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 1){
                updateSecondView();
            }
        }
    };

    @Override
    public int getLayoutId(){
        return R.layout.fragment_registered;
    }

    @Override
    public void initView(){

    }

    @Override
    public void initData(){

    }

    @Override
    public void setUpComponent(){
        if(mRegisteredFragmentComponent == null){
            mRegisteredFragmentComponent = DaggerRegisteredFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .registeredFragmentModule(new RegisteredFragmentModule(this))
                    .build();
            mRegisteredFragmentComponent.inject(this);
        }

    }

    @OnClick({R.id.tv_send_verification, R.id.btn_registered, R.id.btn_cancel})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tv_send_verification:
                mTimer = new Timer();
                mTimer.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        mHandler.sendEmptyMessage(1);
                    }
                }, 0, 1000);
                break;
            case R.id.btn_registered:
                mRegisteredFragmentPresenter.registerJudgment(this, mEtName.getText()
                        .toString(), mEtPassword.getText().toString(), mEtPasswordComfire.getText()
                                                                      .toString(), mEtUsername.getText()
                                                                      .toString(), mEtVerification.getText()
                                                                      .toString());
                break;
            case R.id.btn_cancel:
                mRegisteredFragmentPresenter.notifyMainActivityReplace(getContext());
                break;
        }
    }

    private void updateSecondView(){
        if(second <= 0){
            mTvSendVerification.setText("重新发送");
            second = 5;
            mTimer.cancel();
        } else{
            mTvSendVerification.setText(second-- + "秒后重新发送");
        }
    }

    @Override
    public void registerSuccess(){

    }

    @Override
    public void registerFail(){

    }
}
