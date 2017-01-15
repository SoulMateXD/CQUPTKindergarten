package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import com.cqupt.kindergarten.injection.component.DaggerLoginFragmentComponent;
import com.cqupt.kindergarten.injection.component.LoginFragmentComponent;
import com.cqupt.kindergarten.injection.module.LoginFragmentModule;
import com.cqupt.kindergarten.presenter.LoginFragmentPresenter;
import com.cqupt.kindergarten.ui.ui_interface.ILoginFragmentInterface;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements ILoginFragmentInterface{

    @Inject
    LoginFragmentPresenter mLoginFragmentPresenter;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_usernameText)
    TextInputLayout mEtUsernameText;
    @BindView(R.id.phone)
    LinearLayout mPhone;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_passwordText)
    TextInputLayout mEtPasswordText;
    @BindView(R.id.pass)
    LinearLayout mPass;
    @BindView(R.id.tv_find_the_pass)
    TextView mTvFindThePass;
    @BindView(R.id.btn_registered)
    Button mBtnRegistered;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private LoginFragmentComponent mLoginFragmentComponent;

    @Override
    public int getLayoutId(){
        return R.layout.fragment_login;
    }

    @Override
    public void initView(){

    }

    @Override
    public void initData(){

    }

    @Override
    public void setUpComponent(){
        if(null == mLoginFragmentComponent){
            mLoginFragmentComponent = DaggerLoginFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .loginFragmentModule(new LoginFragmentModule(this))
                    .build();
            mLoginFragmentComponent.inject(this);
        }
    }



    @OnClick({R.id.tv_find_the_pass, R.id.btn_registered, R.id.btn_login})
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tv_find_the_pass:
                break;
            case R.id.btn_registered:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
