package com.cqupt.kindergarten.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by SoulMateXD on 2017/4/24.
 */

public class Parent extends DataSupport {
    private String sSex;
    private String sName;
    private String sIdentifyId;
    private String sComeAge;
    private String cId;
    private String sComeTime;
    private String sAcount;
    private String sPassword;
    private String sId;
    private String sAddress;
    private String sface;

    public String getSface() {
        return sface;
    }

    public void setSface(String sface) {
        this.sface = sface;
    }

    @Override
    public String toString() {
        return "sSex: " + sSex + " sName: " + sName + " cId: " + cId + " sAcount: " + sAcount + " sId: " + sId;
    }

    public String getsSex() {
        return sSex;
    }

    public void setsSex(String sSex) {
        this.sSex = sSex;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsIdentifyId() {
        return sIdentifyId;
    }

    public void setsIdentifyId(String sIdentifyId) {
        this.sIdentifyId = sIdentifyId;
    }

    public String getsComeAge() {
        return sComeAge;
    }

    public void setsComeAge(String sComeAge) {
        this.sComeAge = sComeAge;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getsComeTime() {
        return sComeTime;
    }

    public void setsComeTime(String sComeTime) {
        this.sComeTime = sComeTime;
    }

    public String getsAcount() {
        return sAcount;
    }

    public void setsAcount(String sAcount) {
        this.sAcount = sAcount;
    }

    public String getsPassword() {
        return sPassword;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsAddress() {
        return sAddress;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }
}
