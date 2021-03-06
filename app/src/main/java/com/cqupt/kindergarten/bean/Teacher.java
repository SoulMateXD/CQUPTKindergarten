package com.cqupt.kindergarten.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by SoulMateXD on 2017/4/24.
 */

public class Teacher extends DataSupport {
    private String tId;
    private String tName;
    private String tPhone;
    private String tSex;
    private String tWorkId;
    private String cId;
    private String tgrade;
    private String tface;
    private String pictureContentid;
    private String movieContentid;

    public String getMovieContentid() {
        return movieContentid;
    }

    public void setMovieContentid(String movieContentid) {
        this.movieContentid = movieContentid;
    }

    public String getPictureContentid() {
        return pictureContentid;
    }

    public void setPictureContentid(String pictureContentid) {
        this.pictureContentid = pictureContentid;
    }

    public String getTface() {
        return tface;
    }

    public void setTface(String tface) {
        this.tface = tface;
    }

    @Override
    public String toString() {
        return  "tName: " + tName + "cId: " + cId + " tgrade: " + tgrade + " tId: " + tId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }

    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }

    public String gettWorkId() {
        return tWorkId;
    }

    public void settWorkId(String tWorkId) {
        this.tWorkId = tWorkId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getTgrade() {
        return tgrade;
    }

    public void setTgrade(String tgrade) {
        this.tgrade = tgrade;
    }
}
