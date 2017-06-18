package com.cqupt.kindergarten.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.ClassFragmentComponent;
import com.cqupt.kindergarten.injection.component.DaggerClassFragmentComponent;
import com.cqupt.kindergarten.injection.module.ClassFragmentModule;
import com.cqupt.kindergarten.presenter.ClassFragmentPresenter;
import com.cqupt.kindergarten.ui.activity.CLassTeacherAvtivity;
import com.cqupt.kindergarten.ui.activity.ClassAlbumActivity;
import com.cqupt.kindergarten.ui.activity.ClassCookBookActivity;
import com.cqupt.kindergarten.ui.activity.ClassTimeTableActivity;
import com.cqupt.kindergarten.ui.activity.HandbookActivity;
import com.cqupt.kindergarten.ui.activity.NewsActivity;
import com.cqupt.kindergarten.ui.ui_interface.IClassFragmentInterface;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends BaseFragment implements IClassFragmentInterface {



    @Inject
    ClassFragmentPresenter mClassFragmentPresenter;
    @BindView(R.id.class_main_timetable)
    RelativeLayout classMainTimetable;
    @BindView(R.id.class_main_notice)
    RelativeLayout classMainNotice;
    @BindView(R.id.class_main_album)
    RelativeLayout classMainAlbum;
    @BindView(R.id.class_main_info_list)
    RelativeLayout classMainList;

    private static final String ARG = "arg";

    //0代表老师， 1代表学生家长
    int loginType = 0;
    //用于区分class和news两个fragment中图鉴和新闻模块的点击事件
    int intentType = TYPE_CLASS;

    private ClassFragmentComponent mClassFragmentComponent;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_class;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpComponent() {
        if (mClassFragmentComponent == null) {
            mClassFragmentComponent = DaggerClassFragmentComponent.builder()
                    .applicationComponent(KindergartenApplication.get().getApplicationComponent())
                    .classFragmentModule(new ClassFragmentModule(this))
                    .build();
            mClassFragmentComponent.inject(this);
        }

    }

    public static Fragment newInstance(int arg){
        ClassFragment classFragment = new ClassFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG, arg);
        classFragment.setArguments(bundle);
        return classFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        Bundle bundle = getArguments();
        loginType = bundle.getInt(ARG);
        //判断登录用户类型，改变名单标题
        if (loginType == 0){
            classMainList.setBackgroundResource(R.mipmap.icon_class_info_list_parent);
        }else if (loginType == 1){
            classMainList.setBackgroundResource(R.mipmap.icon_class_info_list_teacher);
        }
        return rootView;
    }

    @OnClick({R.id.class_main_timetable, R.id.class_main_notice, R.id.class_main_album, R.id.class_main_info_list})
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.class_main_timetable:
                intent = new Intent(getActivity(), ClassTimeTableActivity.class);
                break;
            case R.id.class_main_notice:
                intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("intentType", intentType);
                break;
            case R.id.class_main_album:
                intent = new Intent(getActivity(), HandbookActivity.class);
                intent.putExtra("intentType", intentType);
                break;
            case R.id.class_main_info_list:
                intent = new Intent(getActivity(), CLassTeacherAvtivity.class);
                intent.putExtra("type", loginType);
                break;
        }
        startActivity(intent);
    }
}
