package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

public class InfoForParentBean implements Parcelable {
    private String tId;
    private String tName;
    private String tPhone;
    private String tSex;
    private String tWorkId;
    private String cId;
    private String tgrade;
    private String tface;


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

    public String getTface() {
        return tface;
    }

    public void setTface(String tface) {
        this.tface = tface;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tId);
        dest.writeString(this.tName);
        dest.writeString(this.tPhone);
        dest.writeString(this.tSex);
        dest.writeString(this.tWorkId);
        dest.writeString(this.cId);
        dest.writeString(this.tgrade);
        dest.writeString(this.tface);
    }

    public InfoForParentBean() {
    }

    protected InfoForParentBean(Parcel in) {
        this.tId = in.readString();
        this.tName = in.readString();
        this.tPhone = in.readString();
        this.tSex = in.readString();
        this.tWorkId = in.readString();
        this.cId = in.readString();
        this.tgrade = in.readString();
        this.tface = in.readString();
    }

    public static final Parcelable.Creator<InfoForParentBean> CREATOR = new Parcelable.Creator<InfoForParentBean>() {
        @Override
        public InfoForParentBean createFromParcel(Parcel source) {
            return new InfoForParentBean(source);
        }

        @Override
        public InfoForParentBean[] newArray(int size) {
            return new InfoForParentBean[size];
        }
    };
}
