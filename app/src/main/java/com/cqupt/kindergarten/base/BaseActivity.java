package com.cqupt.kindergarten.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cqupt.kindergarten.R;
import com.cqupt.kindergarten.listener.PermissionsListener;
import com.cqupt.kindergarten.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity{

    private static final long TIME_INTERVAL = 2 * 1000;

    private PermissionsListener mListener;
    private long lastBackTime = 0;
    private int mRequestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
        setUpComponent();
        initView();
        initData();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if(isShouldHideInput(v, ev)){
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event){
        if(v != null && (v instanceof EditText)){
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token){
        if(token != null){
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(this instanceof MainActivity){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                long currentBackTime = System.currentTimeMillis();
                if(currentBackTime - lastBackTime > TIME_INTERVAL){
                    Toast.makeText(this, R.string.press_back_twice, Toast.LENGTH_SHORT).show();
                    lastBackTime = currentBackTime;
                } else{
                    finish();
                    //                    直接点击返回 home
                    //                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    //                    intent.addCategory(Intent.CATEGORY_HOME);
                    //                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //                    startActivity(intent);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 其他 Activity 继承 BaseActivity 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    protected void performRequestPermissions(String desc, String[] permissions, int requestCode, PermissionsListener listener){
        if(permissions == null || permissions.length == 0){
            return;
        }
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

    /**
     * 申请权限前判断是否需要声明
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void requestEachPermissions(String desc, String[] permissions, int requestCode){
        if(shouldShowRequestPermissionRationale(permissions)){// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else{
            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.tips))
                .setMessage(desc)
                .setPositiveButton(getResources().getString(R.string.confrim), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
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
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
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
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
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

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults){
        for(int result : grantResults){
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    public abstract void setUpComponent();

    public abstract void initView();

    public abstract void initData();
    public abstract int getLayoutID();
}
