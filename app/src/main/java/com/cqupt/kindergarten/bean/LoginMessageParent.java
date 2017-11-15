package com.cqupt.kindergarten.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by SoulMateXD on 2017/4/24.
 */

public class LoginMessageParent extends DataSupport{

    private String Appid;
    private String msg;
    private Parent Object;

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

    public Parent getObject() {
        return Object;
    }

    public void setObject(Parent object) {
        Object = object;
    }

    @Override
    public String toString() {
        return "Appid: " + Appid + "  Msg: " + msg + " Parent:" + Object;
    }
}
