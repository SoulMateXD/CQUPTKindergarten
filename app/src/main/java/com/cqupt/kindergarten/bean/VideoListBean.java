package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

public class VideoListBean implements Parcelable {
    private String mcpeople ="";
    private String mctime="";
    private String mccontent="";
    private String mcclassid="";
    private String mcid="";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mcpeople);
        dest.writeString(this.mctime);
        dest.writeString(this.mccontent);
        dest.writeString(this.mcclassid);
        dest.writeString(this.mcid);
    }

    public VideoListBean() {
    }

    public VideoListBean(String mccontent, String mcid) {
        this.mccontent = mccontent;
        this.mcid = mcid;
    }

    protected VideoListBean(Parcel in) {
        this.mcpeople = in.readString();
        this.mctime = in.readString();
        this.mccontent = in.readString();
        this.mcclassid = in.readString();
        this.mcid = in.readString();
    }

    public static final Parcelable.Creator<VideoListBean> CREATOR = new Parcelable.Creator<VideoListBean>() {
        @Override
        public VideoListBean createFromParcel(Parcel source) {
            return new VideoListBean(source);
        }

        @Override
        public VideoListBean[] newArray(int size) {
            return new VideoListBean[size];
        }
    };

    public String getMcpeople() {
        return mcpeople;
    }

    public void setMcpeople(String mcpeople) {
        this.mcpeople = mcpeople;
    }

    public String getMctime() {
        return mctime;
    }

    public void setMctime(String mctime) {
        this.mctime = mctime;
    }

    public String getMccontent() {
        return mccontent;
    }

    public void setMccontent(String mccontent) {
        this.mccontent = mccontent;
    }

    public String getMcclassid() {
        return mcclassid;
    }

    public void setMcclassid(String mcclassid) {
        this.mcclassid = mcclassid;
    }

    public String getMcid() {
        return mcid;
    }

    public void setMcid(String mcid) {
        this.mcid = mcid;
    }
}
