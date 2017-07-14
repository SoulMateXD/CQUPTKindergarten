package com.cqupt.kindergarten.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by SoulMateXD on 2017/4/24.
 */

public class LoginMessageTeacher extends DataSupport {

    private String Appid;
    private String msg;
    private Teacher Object;

//    @Override
//    public String toString() {
//        return "Appid: " + Appid + "  Msg: " + msg + " Parent:" + Object[0];
//    }

    public String getAppid() {
        return Appid;
    }

    public void setAppid(String appid) {
        Appid = appid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Teacher getObject() {
        return Object;
    }

    public void setObject(Teacher object) {
        Object = object;
    }
}
