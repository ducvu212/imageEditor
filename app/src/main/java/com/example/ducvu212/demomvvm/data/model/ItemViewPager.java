package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ItemViewPager implements Parcelable {

    private String mPath;
    private String mUserName;
    private boolean mLikeByUser;
    public static final Creator<ItemViewPager> CREATOR = new Creator<ItemViewPager>() {
        @Override
        public ItemViewPager createFromParcel(Parcel source) {
            return new ItemViewPager(source);
        }

        @Override
        public ItemViewPager[] newArray(int size) {
            return new ItemViewPager[size];
        }
    };

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    private boolean mDownloaded;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    private ItemViewPager(Builder builder) {
        setPath(builder.mPath);
        setUserName(builder.mUserName);
        setLikeByUser(builder.mLikeByUser);
        setDownloaded(builder.mDownloaded);
    }

    protected ItemViewPager(Parcel in) {
        mPath = in.readString();
        mUserName = in.readString();
        mLikeByUser = in.readByte() != 0;
        mDownloaded = in.readByte() != 0;
    }

    public boolean isLikeByUser() {
        return mLikeByUser;
    }

    public void setLikeByUser(boolean likeByUser) {
        mLikeByUser = likeByUser;
    }

    public boolean isDownloaded() {
        return mDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        mDownloaded = downloaded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPath);
        dest.writeString(mUserName);
        dest.writeByte(mLikeByUser ? (byte) 1 : (byte) 0);
        dest.writeByte(mDownloaded ? (byte) 1 : (byte) 0);
    }

    public static final class Builder {
        private String mPath;
        private String mUserName;
        private boolean mLikeByUser;
        private boolean mDownloaded;

        public Builder() {
        }

        public Builder mPath(String path) {
            mPath = path;
            return this;
        }

        public Builder mUserName(String val) {
            mUserName = val;
            return this;
        }

        public Builder mLikeByUser(boolean val) {
            mLikeByUser = val;
            return this;
        }

        public Builder mDownloaded(boolean val) {
            mDownloaded = val;
            return this;
        }

        public ItemViewPager build() {
            return new ItemViewPager(this);
        }
    }
}
