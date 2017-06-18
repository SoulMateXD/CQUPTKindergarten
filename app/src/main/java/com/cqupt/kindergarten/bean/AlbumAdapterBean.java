package com.cqupt.kindergarten.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SoulMateXD on 2017/5/19.
 */

public class AlbumAdapterBean implements Parcelable {
    private String imageUrl;
    private String imageNumber;
    private String albumTitle;
    private String albumId;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public AlbumAdapterBean(String imageUrl, String imageNumber, String albumTitle, String albumId) {
        this.imageUrl = imageUrl;
        this.imageNumber = imageNumber;
        this.albumTitle = albumTitle;
        this.albumId = albumId;
    }

    protected AlbumAdapterBean(Parcel in) {
        imageUrl = in.readString();
        imageNumber = in.readString();
        albumTitle = in.readString();
        albumId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(imageNumber);
        dest.writeString(albumTitle);
        dest.writeString(albumId);
    }

    public static final Creator<AlbumAdapterBean> CREATOR = new Creator<AlbumAdapterBean>() {
        @Override
        public AlbumAdapterBean createFromParcel(Parcel in) {
            AlbumAdapterBean bean =
                    new AlbumAdapterBean(in.readString(), in.readString(), in.readString(), in.readString());
            return bean;
        }

        @Override
        public AlbumAdapterBean[] newArray(int size) {
            return new AlbumAdapterBean[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(String imageNumber) {
        this.imageNumber = imageNumber;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

}