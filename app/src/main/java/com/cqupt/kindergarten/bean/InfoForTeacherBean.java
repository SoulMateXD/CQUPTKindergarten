package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

public class InfoForTeacherBean implements Parcelable {
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
    private String sFace;

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

    public String getsFace() {
        return sFace;
    }

    public void setsFace(String sFace) {
        this.sFace = sFace;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sSex);
        dest.writeString(this.sName);
        dest.writeString(this.sIdentifyId);
        dest.writeString(this.sComeAge);
        dest.writeString(this.cId);
        dest.writeString(this.sComeTime);
        dest.writeString(this.sAcount);
        dest.writeString(this.sPassword);
        dest.writeString(this.sId);
        dest.writeString(this.sAddress);
        dest.writeString(this.sFace);
    }

    public InfoForTeacherBean() {
    }

    protected InfoForTeacherBean(Parcel in) {
        this.sSex = in.readString();
        this.sName = in.readString();
        this.sIdentifyId = in.readString();
        this.sComeAge = in.readString();
        this.cId = in.readString();
        this.sComeTime = in.readString();
        this.sAcount = in.readString();
        this.sPassword = in.readString();
        this.sId = in.readString();
        this.sAddress = in.readString();
        this.sFace = in.readString();
    }

    public static final Parcelable.Creator<InfoForTeacherBean> CREATOR = new Parcelable.Creator<InfoForTeacherBean>() {
        @Override
        public InfoForTeacherBean createFromParcel(Parcel source) {
            return new InfoForTeacherBean(source);
        }

        @Override
        public InfoForTeacherBean[] newArray(int size) {
            return new InfoForTeacherBean[size];
        }
    };
}
