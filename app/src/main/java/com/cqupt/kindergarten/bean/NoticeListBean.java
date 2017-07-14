package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/7/13.
 */

public class NoticeListBean implements Parcelable {
    private int idnews;
    private String issuer;
    private String time;
    private String state;
    private String title;
    private String message;
    private String kind;
    private String url1;
    private String url2;
    private int totalPage;

    public int getIdnews() {
        return idnews;
    }

    public void setIdnews(int idnews) {
        this.idnews = idnews;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idnews);
        dest.writeString(this.issuer);
        dest.writeString(this.time);
        dest.writeString(this.state);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.kind);
        dest.writeString(this.url1);
        dest.writeString(this.url2);
        dest.writeInt(this.totalPage);
    }

    public NoticeListBean(String title, String message) {
        this.title = title;
        this.message = message;
    }

    protected NoticeListBean(Parcel in) {
        this.idnews = in.readInt();
        this.issuer = in.readString();
        this.time = in.readString();
        this.state = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.kind = in.readString();
        this.url1 = in.readString();
        this.url2 = in.readString();
        this.totalPage = in.readInt();
    }

    public static final Parcelable.Creator<NoticeListBean> CREATOR = new Parcelable.Creator<NoticeListBean>() {
        @Override
        public NoticeListBean createFromParcel(Parcel source) {
            return new NoticeListBean(source);
        }

        @Override
        public NoticeListBean[] newArray(int size) {
            return new NoticeListBean[size];
        }
    };
}
