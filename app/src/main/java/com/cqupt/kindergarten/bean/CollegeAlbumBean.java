package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/6/1.
 */

public class CollegeAlbumBean implements Parcelable {
    private String time;
    private ArrayList<ImageItemBean> T;

    public CollegeAlbumBean(String time, ArrayList<ImageItemBean> imageUrls) {
        this.time = time;
        this.T = imageUrls;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<ImageItemBean> getT() {
        return T;
    }

    public void setT(ArrayList<ImageItemBean> imageUrls) {
        this.T = imageUrls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeTypedList(this.T);
    }

    protected CollegeAlbumBean(Parcel in) {
        this.time = in.readString();
        this.T = in.createTypedArrayList(ImageItemBean.CREATOR);
    }

    public static final Parcelable.Creator<CollegeAlbumBean> CREATOR = new Parcelable.Creator<CollegeAlbumBean>() {
        @Override
        public CollegeAlbumBean createFromParcel(Parcel source) {
            return new CollegeAlbumBean(source);
        }

        @Override
        public CollegeAlbumBean[] newArray(int size) {
            return new CollegeAlbumBean[size];
        }
    };
}
