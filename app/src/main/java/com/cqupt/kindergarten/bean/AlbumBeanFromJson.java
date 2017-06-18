package com.cqupt.kindergarten.bean;

/**
 * Created by SoulMateXD on 2017/5/21.
 */

public class AlbumBeanFromJson {

    private String picid;
    private String picname;
    private String picmdescribe;
    private String picdate;
    private String picface;

    public String getPicid() {
        return picid;
    }

    @Override
    public String toString() {
        return picid + picname + picmdescribe + picdate + picface + "\\\\\\";
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    public String getPicmdescribe() {
        return picmdescribe;
    }

    public void setPicmdescribe(String picmdescribe) {
        this.picmdescribe = picmdescribe;
    }

    public String getPicdate() {
        return picdate;
    }

    public void setPicdate(String picdate) {
        this.picdate = picdate;
    }

    public String getPicface() {
        return picface;
    }

    public void setPicface(String picface) {
        this.picface = picface;
    }
}
