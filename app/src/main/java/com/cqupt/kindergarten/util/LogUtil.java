package com.cqupt.kindergarten.util;

import android.util.Log;

/**
 * Created by SoulMateXD on 2017/4/11.
 */

public class LogUtil {
    private String tag;
    private boolean isDebug = true;

    public LogUtil(String tag) {
        this.tag = tag;
    }

    public void v(String msg){
        if (isDebug)
            Log.v(tag, msg);
    }

    public void d(String msg){
        if (isDebug)
            Log.d(tag, msg);
    }

    public void i(String msg){
        if (isDebug)
            Log.i(tag, msg);
    }

    public void w(String msg){
        if (isDebug)
            Log.w(tag, msg);
    }

    public void e(String msg){
        if (isDebug)
            Log.e(tag, msg);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    public String getTag() {
        return tag;
    }

    public boolean isDebug() {
        return isDebug;
    }
}
