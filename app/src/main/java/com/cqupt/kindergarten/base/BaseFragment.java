package com.cqupt.kindergarten.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.listener.PermissionsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{

    public View mView;
    private PermissionsListener mListener;
    private int mRequestCode;

    /*
    *  class，和 news 两个fragment中，公告和图鉴模块，用于跳转判断
    * */
    public static int TYPE_CLASS = 0;
    public static int TYPE_NEWS = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mView);
        setUpComponent();
        initView();
        initData();
        return mView;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    @Override
    public void onDetach(){
        super.onDetach();
    }

    //模板方法,留给子类编写
    public abstract void setUpComponent();

    protected void performRequestPermissions(String desc, String[] permissions, int requestCode, PermissionsListener listener){
        if(permissions == null || permissions.length == 0) return;
        mRequestCode = requestCode;
        mListener = listener;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkEachSelfPermission(permissions)){// 检查是否声明了权限
                requestEachPermissions(desc, permissions, requestCode);
            } else{// 已经申请权限
                if(mListener != null){
                    mListener.onPermissionGranted();
                }
            }
        } else{
            if(mListener != null){
                mListener.onPermissionGranted();
            }
        }
    }

    private void requestEachPermissions(String desc, String[] permissions, int requestCode){
        if(shouldShowRequestPermissionRationale(permissions)){// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else{
            requestPermissions(permissions, requestCode);
        }
    }

    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.confrim), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        requestPermissions(permissions, requestCode);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancle), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        for(String permission : permissions){
            if(shouldShowRequestPermissionRationale(permission)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */

    private boolean checkEachSelfPermission(String[] permissions){
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == mRequestCode){
            if(checkEachPermissionsGranted(grantResults)){
                if(mListener != null){
                    mListener.onPermissionGranted();
                }
            } else{// 用户拒绝申请权限
                if(mListener != null){
                    List<String> permissionList = new ArrayList<>();
                    Collections.addAll(permissionList, permissions);
                    mListener.onPermissionDenied(permissionList);
                }
            }
        }
    }

    private boolean checkEachPermissionsGranted(int[] grantResults){
        for(int result : grantResults){
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

}
