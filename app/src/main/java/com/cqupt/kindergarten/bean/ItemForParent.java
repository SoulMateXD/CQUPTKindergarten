package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

/*
*   infoList 中 家长界面，老师的信息
* */
public class ItemForParent implements Parcelable {
    private String teacherName;
    private String teacherTel;



    public ItemForParent(String teacherName, String teacherTel) {
        this.teacherName = teacherName;
        this.teacherTel = teacherTel;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherTel() {
        return teacherTel;
    }

    public void setTeacherTel(String teacherTel) {
        this.teacherTel = teacherTel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.teacherName);
        dest.writeString(this.teacherTel);
    }

    protected ItemForParent(Parcel in) {
        this.teacherName = in.readString();
        this.teacherTel = in.readString();
    }

    public static final Parcelable.Creator<ItemForParent> CREATOR = new Parcelable.Creator<ItemForParent>() {
        @Override
        public ItemForParent createFromParcel(Parcel source) {
            return new ItemForParent(source);
        }

        @Override
        public ItemForParent[] newArray(int size) {
            return new ItemForParent[size];
        }
    };
}
