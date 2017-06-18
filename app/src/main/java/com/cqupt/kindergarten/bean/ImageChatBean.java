package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/6/5.
 */

public class ImageChatBean implements Parcelable {
    private String comId;  //评论id
    private String poneId;  //第一个人的名字
    private String ptwoId;  //第二个人的名字
    private String comContent;  //评论内容
    private int comcount;   //评论总数  仅在第一个数据中显示评论总数，其它的都为0
    private String xId;     //相片id
    private String comTime; //评论时间



    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getPoneId() {
        return poneId;
    }

    public void setPoneId(String poneId) {
        this.poneId = poneId;
    }

    public String getPtwoId() {
        return ptwoId;
    }

    public void setPtwoId(String ptwoId) {
        this.ptwoId = ptwoId;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public int getComcount() {
        return comcount;
    }

    public void setComcount(int comcount) {
        this.comcount = comcount;
    }

    public String getxId() {
        return xId;
    }

    public void setxId(String xId) {
        this.xId = xId;
    }

    public String getComTime() {
        return comTime;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.comId);
        dest.writeString(this.poneId);
        dest.writeString(this.ptwoId);
        dest.writeString(this.comContent);
        dest.writeInt(this.comcount);
        dest.writeString(this.xId);
        dest.writeString(this.comTime);
    }

    public ImageChatBean() {
    }

    protected ImageChatBean(Parcel in) {
        this.comId = in.readString();
        this.poneId = in.readString();
        this.ptwoId = in.readString();
        this.comContent = in.readString();
        this.comcount = in.readInt();
        this.xId = in.readString();
        this.comTime = in.readString();
    }

    public static final Parcelable.Creator<ImageChatBean> CREATOR = new Parcelable.Creator<ImageChatBean>() {
        @Override
        public ImageChatBean createFromParcel(Parcel source) {
            return new ImageChatBean(source);
        }

        @Override
        public ImageChatBean[] newArray(int size) {
            return new ImageChatBean[size];
        }
    };
}
