package com.cqupt.kindergarten.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.injection.component.ClassFragmentComponent;
import com.cqupt.kindergarten.injection.component.DaggerClassFragmentComponent;
import com.cqupt.kindergarten.injection.module.ClassFragmentModule;
import com.cqupt.kindergarten.presenter.ClassFragmentPresenter;
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
    @BindView(R.id.class_main_cookbook)
    RelativeLayout classMainCookbook;
    @BindView(R.id.class_main_album)
    RelativeLayout classMainAlbum;
    @BindView(R.id.class_main_teacher)
    RelativeLayout classMainTeacher;

    private ClassFragmentComponent mClassFragmentComponent;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.class_main_timetable, R.id.class_main_cookbook, R.id.class_main_album, R.id.class_main_teacher})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.class_main_timetable:
                break;
            case R.id.class_main_cookbook:
                break;
            case R.id.class_main_album:
                break;
            case R.id.class_main_teacher:
                break;
        }
    }
}
