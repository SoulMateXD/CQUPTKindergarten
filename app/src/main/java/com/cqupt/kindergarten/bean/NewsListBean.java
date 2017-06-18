package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SoulMateXD on 2017/5/21.
 */

public class NewsListBean implements Parcelable {
    private String idnews;
    private String issuer;
    private String time;
    private String state;
    private String title;
    private String message;
    private String kind;
    private String url1;
    private String url2;
    private String totalPage;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idnews);
        dest.writeString(this.issuer);
        dest.writeString(this.time);
        dest.writeString(this.state);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.kind);
        dest.writeString(this.url1);
        dest.writeString(this.url2);
        dest.writeString(this.totalPage);
    }

    public NewsListBean() {
    }

    protected NewsListBean(Parcel in) {
        this.idnews = in.readString();
        this.issuer = in.readString();
        this.time = in.readString();
        this.state = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.kind = in.readString();
        this.url1 = in.readString();
        this.url2 = in.readString();
        this.totalPage = in.readString();
    }

    public static final Creator<NewsListBean> CREATOR = new Creator<NewsListBean>() {
        @Override
        public NewsListBean createFromParcel(Parcel source) {
            return new NewsListBean(source);
        }

        @Override
        public NewsListBean[] newArray(int size) {
            return new NewsListBean[size];
        }
    };

    public String getIdnews() {
        return idnews;
    }

    public void setIdnews(String idnews) {
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

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public static Creator<NewsListBean> getCREATOR() {
        return CREATOR;
    }
}
