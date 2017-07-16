package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqupt.kindergarten.ui.activity.CLassTeacherAvtivity;

import java.util.ArrayList;

/**
 * Created by SoulMateXD on 2017/7/15.
 */

//
// ListInfo界面中
// 教师界面， item的信息
public class ItemForTeacher implements Parcelable {
    private String childName;
    private ArrayList<ItemForTeacherParent> parents;

    public ItemForTeacher(String childName, ArrayList<ItemForTeacherParent> parents) {
        this.childName = childName;
        this.parents = parents;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public ArrayList<ItemForTeacherParent> getParents() {
        return parents;
    }

    public void setParents(ArrayList<ItemForTeacherParent> parents) {
        this.parents = parents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.childName);
        dest.writeList(this.parents);
    }

    public ItemForTeacher() {
    }

    protected ItemForTeacher(Parcel in) {
        this.childName = in.readString();
        this.parents = new ArrayList<ItemForTeacherParent>();
        in.readList(this.parents, ItemForTeacherParent.class.getClassLoader());
    }

    public static final Parcelable.Creator<ItemForTeacher> CREATOR = new Parcelable.Creator<ItemForTeacher>() {
        @Override
        public ItemForTeacher createFromParcel(Parcel source) {
            return new ItemForTeacher(source);
        }

        @Override
        public ItemForTeacher[] newArray(int size) {
            return new ItemForTeacher[size];
        }
    };
}
