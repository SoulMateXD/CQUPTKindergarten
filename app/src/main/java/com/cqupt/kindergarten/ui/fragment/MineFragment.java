package com.cqupt.kindergarten.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.kindergarten.KindergartenApplication;
import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.base.BaseFragment;
import com.cqupt.kindergarten.bean.LoginMessageParent;
import com.cqupt.kindergarten.bean.LoginMessageTeacher;
import com.cqupt.kindergarten.bean.Parent;
import com.cqupt.kindergarten.bean.Teacher;
import com.cqupt.kindergarten.injection.component.DaggerMineFragmentComponent;
import com.cqupt.kindergarten.injection.component.MineFragmentComponent;
import com.cqupt.kindergarten.injection.module.MineFragmentModule;
import com.cqupt.kindergarten.presenter.MineFragmentPresenter;
import com.cqupt.kindergarten.ui.activity.LoginActivity;
import com.cqupt.kindergarten.ui.activity.MainActivity;
import com.cqupt.kindergarten.ui.ui_interface.IMineFragmentInterface;
import com.makeramen.roundedimageview.RoundedImageView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    private static final String LOGIN_SHARED_PREFRERNCES = "LoginPreferences";
    private static final String IS_LOGIN = "isLogin";
    public static final int CHOOSE_PHOTO = 2;
    private static final int TEACHER = 0;
    private static final int PARENT = 1;

    private String Appid;
    private int type;
    private SharedPreferences sharedPreferences;

    private MineFragmentComponent mMineFragmentComponent;
    private MainActivity mainActivity;

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

        if (type == PARENT){
            Parent parent = DataSupport.findFirst(Parent.class);
            mineUserClass.setText("所在班级 : " + parent.getcId());
            mineUserName.setText("昵称 : " + parent.getsName());
            mineUserAppid.setText("账号 : " + Appid);
        }else if (type == TEACHER){
            Teacher teacher = DataSupport.findFirst(Teacher.class);
            mineUserClass.setText("任课班级 : " + teacher.getcId());
            mineUserName.setText("昵称 : " + teacher.gettName());
            mineUserAppid.setText("账号 : " + Appid);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ming_user_image, R.id.mine_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ming_user_image:
                chooseFromAlbum();
                break;
            case R.id.mine_exit:
                exit();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == -1 && data != null){
                    Uri imageUri = data.getData();
                    String imagePath = getImagePath(imageUri, null);
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    mingUserImage.setImageBitmap(bitmap);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(mainActivity, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    /*
    *   退出删除登录信息
    * */
    private void exit(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, false);
        editor.apply();

        if (type == PARENT){
            DataSupport.deleteAll(LoginMessageParent.class);
            DataSupport.deleteAll(Parent.class);
        }else if (type == TEACHER){
            DataSupport.deleteAll(LoginMessageTeacher.class);
            DataSupport.deleteAll(Teacher.class);
        }

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        mainActivity.finish();
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = mainActivity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void chooseFromAlbum() {
        if (ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
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
}
