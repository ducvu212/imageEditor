package com.example.ducvu212.demomvvm.data.model;

import java.util.List;

public class Album {
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
}
