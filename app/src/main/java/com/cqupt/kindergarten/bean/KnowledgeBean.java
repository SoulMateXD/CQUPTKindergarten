package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/6/8.
 */

public class KnowledgeBean implements Parcelable {
    private int idnews;
    private String issuer;
    private String time;
    private String title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        dest.writeString(this.issuer);
        dest.writeInt(this.idnews);
        dest.writeString(this.time);
        dest.writeString(this.title);
        dest.writeString(this.kind);
        dest.writeString(this.url1);
        dest.writeString(this.url2);
        dest.writeInt(this.totalPage);
    }

    public KnowledgeBean() {
    }

    protected KnowledgeBean(Parcel in) {
        this.issuer = in.readString();
        this.idnews = in.readInt();
        this.time = in.readString();
        this.title = in.readString();
        this.kind = in.readString();
        this.url1 = in.readString();
        this.url2 = in.readString();
        this.totalPage = in.readInt();
    }

    public static final Parcelable.Creator<KnowledgeBean> CREATOR = new Parcelable.Creator<KnowledgeBean>() {
        @Override
        public KnowledgeBean createFromParcel(Parcel source) {
            return new KnowledgeBean(source);
        }

        @Override
        public KnowledgeBean[] newArray(int size) {
            return new KnowledgeBean[size];
        }
    };
}
