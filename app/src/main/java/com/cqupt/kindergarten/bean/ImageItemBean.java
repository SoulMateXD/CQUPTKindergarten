package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/6/1.
 */

public class ImageItemBean implements Parcelable {
    private String xcDate;
    private String xcId; //相片ID
    private String xcAdress;
    private String pLike;
    private String pId; //相册ID

    public String getpLike() {
        return pLike;
    }

    public void setpLike(String pLike) {
        this.pLike = pLike;
    }

    public ImageItemBean(String xcDate, String xcId, String xcAdress, String pId) {
        this.xcDate = xcDate;
        this.xcId = xcId;
        this.xcAdress = xcAdress;
        this.pId = pId;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.xcDate);
        dest.writeString(this.xcId);
        dest.writeString(this.xcAdress);
        dest.writeString(this.pId);
        dest.writeString(this.pLike);
    }

    protected ImageItemBean(Parcel in) {
        this.xcDate = in.readString();
        this.xcId = in.readString();
        this.xcAdress = in.readString();
        this.pId = in.readString();
        this.pLike = in.readString();
    }

    public static final Parcelable.Creator<ImageItemBean> CREATOR = new Parcelable.Creator<ImageItemBean>() {
        @Override
        public ImageItemBean createFromParcel(Parcel source) {
            return new ImageItemBean(source);
        }

        @Override
        public ImageItemBean[] newArray(int size) {
            return new ImageItemBean[size];
        }
    };

    public String getXcDate() {
        return xcDate;
    }

    public void setXcDate(String xcDate) {
        this.xcDate = xcDate;
    }

    public String getXcId() {
        return xcId;
    }

    public void setXcId(String xcId) {
        this.xcId = xcId;
    }

    public String getXcAdress() {
        return xcAdress;
    }

    public void setXcAdress(String xcAdress) {
        this.xcAdress = xcAdress;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
