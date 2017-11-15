package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/9/24.
 */

public class VideoCollectionBean implements Parcelable {
    private String cmname;
    private String cmid;
    private String cmuserid;

    public String getCmname() {
        return cmname;
    }

    public void setCmname(String cmname) {
        this.cmname = cmname;
    }

    public String getCmid() {
        return cmid;
    }

    public void setCmid(String cmid) {
        this.cmid = cmid;
    }

    public String getCmuserid() {
        return cmuserid;
    }

    public void setCmuserid(String cmuserid) {
        this.cmuserid = cmuserid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cmname);
        dest.writeString(this.cmid);
        dest.writeString(this.cmuserid);
    }

    public VideoCollectionBean() {
    }

    protected VideoCollectionBean(Parcel in) {
        this.cmname = in.readString();
        this.cmid = in.readString();
        this.cmuserid = in.readString();
    }

    public static final Parcelable.Creator<VideoCollectionBean> CREATOR = new Parcelable.Creator<VideoCollectionBean>() {
        @Override
        public VideoCollectionBean createFromParcel(Parcel source) {
            return new VideoCollectionBean(source);
        }

        @Override
        public VideoCollectionBean[] newArray(int size) {
            return new VideoCollectionBean[size];
        }
    };
}
