package com.cqupt.kindergarten.listener;

import java.util.List;

/**
 * Created by inferno on 2017/1/14.
 */

public interface PermissionsListener{
    //同意权限
    void onPermissionGranted();
    //拒绝权限
    void onPermissionDenied(List<String> permissions);
}
