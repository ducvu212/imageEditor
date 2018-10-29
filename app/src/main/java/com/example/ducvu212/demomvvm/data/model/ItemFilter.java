package com.example.ducvu212.demomvvm.data.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ItemFilter implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mPath, flags);
        dest.writeString(this.mName);
        dest.writeString(this.mPathGradient);
        dest.writeParcelable(this.mFilter, flags);
        dest.writeInt(this.mCountClick);
    }

    protected ItemFilter(Parcel in) {
        this.mPath = in.readParcelable(Bitmap.class.getClassLoader());
        this.mName = in.readString();
        this.mPathGradient = in.readString();
        this.mFilter = in.readParcelable(Bitmap.class.getClassLoader());
        this.mCountClick = in.readInt();
    }

    public static final Parcelable.Creator<ItemFilter> CREATOR =
            new Parcelable.Creator<ItemFilter>() {
                @Override
                public ItemFilter createFromParcel(Parcel source) {
                    return new ItemFilter(source);
                }

                @Override
                public ItemFilter[] newArray(int size) {
                    return new ItemFilter[size];
                }
            };
}
