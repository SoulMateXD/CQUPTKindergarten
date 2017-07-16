package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

    /*
    *   ListInfo界面中，教师界面的item信息的
    *   家长信息
    * */
public class ItemForTeacherParent implements Parcelable {
    private String parentName;
    private String parentTel;

    public ItemForTeacherParent(String parentName, String parentTel) {
        this.parentName = parentName;
        this.parentTel = parentTel;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentTel() {
        return parentTel;
    }

    public void setParentTel(String parentTel) {
        this.parentTel = parentTel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentName);
        dest.writeString(this.parentTel);
    }

    public ItemForTeacherParent() {
    }

    protected ItemForTeacherParent(Parcel in) {
        this.parentName = in.readString();
        this.parentTel = in.readString();
    }

    public static final Parcelable.Creator<ItemForTeacherParent> CREATOR = new Parcelable.Creator<ItemForTeacherParent>() {
        @Override
        public ItemForTeacherParent createFromParcel(Parcel source) {
            return new ItemForTeacherParent(source);
        }

        @Override
        public ItemForTeacherParent[] newArray(int size) {
            return new ItemForTeacherParent[size];
        }
    };
}
