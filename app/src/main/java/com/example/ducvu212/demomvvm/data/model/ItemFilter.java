package com.example.ducvu212.demomvvm.data.model;

import android.graphics.Bitmap;

public class ItemFilter{
    private Bitmap mPath;
    private String mName;
    private String mPathGradient;
    private Bitmap mFilter;
    private int mCountClick;

    public ItemFilter(Bitmap path, String name, String pathGradient) {
        mPath = path;
        mName = name;
        mPathGradient = pathGradient;
    }

    public Bitmap getFilter() {
        return mFilter;
    }

    public void setFilter(Bitmap filter) {
        mFilter = filter;
    }

    public int getCountClick() {
        return mCountClick;
    }

    public void setCountClick(int countClick) {
        mCountClick = countClick;
    }

    public Bitmap getPath() {
        return mPath;
    }

    public void setPath(Bitmap path) {
        mPath = path;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPathGradient() {
        return mPathGradient;
    }

    public void setPathGradient(String pathGradient) {
        mPathGradient = pathGradient;
    }
}
