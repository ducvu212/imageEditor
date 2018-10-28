package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Album implements Parcelable {
    private String mPath;
    private String mTotalImage;
    private String mTitle;
    private List<Image> mImages;

    public Album(String path, String totalImage, String title) {
        mPath = path;
        mTotalImage = totalImage;
        mTitle = title;
    }

    public Album(String title, List<Image> images) {
        mTitle = title;
        mImages = images;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getTotalImage() {
        return mTotalImage;
    }

    public void setTotalImage(String totalImage) {
        mTotalImage = totalImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPath);
        dest.writeString(this.mTotalImage);
        dest.writeString(this.mTitle);
        dest.writeTypedList(this.mImages);
    }

    protected Album(Parcel in) {
        this.mPath = in.readString();
        this.mTotalImage = in.readString();
        this.mTitle = in.readString();
        this.mImages = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
