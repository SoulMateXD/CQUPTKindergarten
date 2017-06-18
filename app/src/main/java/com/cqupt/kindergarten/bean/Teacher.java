package com.cqupt.kindergarten.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by SoulMateXD on 2017/4/24.
 */

public class Teacher extends DataSupport {
    private String tId;
    private String tName;
    private String tPhone;
    private String tSex;
    private String tWorkId;
    private String cId;
    private String tgrade;

    @Override
    public String toString() {
        return  "tName: " + tName + "cId: " + cId + " tgrade: " + tgrade + " tId: " + tId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }

    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }

    public String gettWorkId() {
        return tWorkId;
    }

    public void settWorkId(String tWorkId) {
        this.tWorkId = tWorkId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getTgrade() {
        return tgrade;
    }

    public void setTgrade(String tgrade) {
        this.tgrade = tgrade;
    }
}
