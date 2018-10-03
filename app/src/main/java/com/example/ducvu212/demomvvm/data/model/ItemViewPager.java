package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ItemViewPager implements Parcelable {

    public static final Parcelable.Creator<ItemViewPager> CREATOR =
            new Parcelable.Creator<ItemViewPager>() {
                @Override
                public ItemViewPager createFromParcel(Parcel source) {
                    return new ItemViewPager(source);
                }

                @Override
                public ItemViewPager[] newArray(int size) {
                    return new ItemViewPager[size];
                }
            };
    private String mPath;

    private ItemViewPager(Builder builder) {
        setPath(builder.mPath);
    }

    protected ItemViewPager(Parcel in) {
        this.mPath = in.readString();
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPath);
    }

    public static final class Builder {
        private String mPath;

        public Builder() {
        }

        public Builder mPath(String path) {
            mPath = path;
            return this;
        }

        public ItemViewPager build() {
            return new ItemViewPager(this);
        }
    }
}
