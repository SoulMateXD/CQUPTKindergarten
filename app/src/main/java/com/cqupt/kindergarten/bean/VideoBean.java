package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

public class VideoBean implements Parcelable {
    private String mvDate;
    private String mvId;
    private String mvAdress;
    private String mvLike;
    private String mcId;
    private String mface;

    public String getMvDate() {
        return mvDate;
    }

    public void setMvDate(String mvDate) {
        this.mvDate = mvDate;
    }

    public String getMvId() {
        return mvId;
    }

    public void setMvId(String mvId) {
        this.mvId = mvId;
    }

    public String getMvAdress() {
        return mvAdress;
    }

    public void setMvAdress(String mvAdress) {
        this.mvAdress = mvAdress;
    }

    public String getMvLike() {
        return mvLike;
    }

    public void setMvLike(String mvLike) {
        this.mvLike = mvLike;
    }

    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }

    public String getMface() {
        return mface;
    }

    public void setMface(String mface) {
        this.mface = mface;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mvDate);
        dest.writeString(this.mvId);
        dest.writeString(this.mvAdress);
        dest.writeString(this.mvLike);
        dest.writeString(this.mcId);
        dest.writeString(this.mface);
    }

    public VideoBean() {
    }

    protected VideoBean(Parcel in) {
        this.mvDate = in.readString();
        this.mvId = in.readString();
        this.mvAdress = in.readString();
        this.mvLike = in.readString();
        this.mcId = in.readString();
        this.mface = in.readString();
    }

    public static final Parcelable.Creator<VideoBean> CREATOR = new Parcelable.Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel source) {
            return new VideoBean(source);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };
}
