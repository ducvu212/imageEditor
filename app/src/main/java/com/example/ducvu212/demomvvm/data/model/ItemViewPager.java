package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CuD HniM on 18/10/03.
 */
//@Entity(tableName = "images")
public class ItemViewPager implements Parcelable {

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
    //    @NonNull
    //    @PrimaryKey(autoGenerate = true)
    //    @ColumnInfo(name = "id")
    private int mId;
    //    @ColumnInfo(name = "path")
    private String mPath;
    //    @ColumnInfo(name = "username")
    private String mUserName;
    //    @ColumnInfo(name = "like")
    private boolean mLikeByUser;
    private String mImageId;

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    private String mRawImage;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    //    @ColumnInfo(name = "download")
    private boolean mDownloaded;

    private ItemViewPager(Builder builder) {
        setId(builder.mId);
        setPath(builder.mPath);
        setUserName(builder.mUserName);
        setLikeByUser(builder.mLikeByUser);
        setImageId(builder.mImageId);
        setRawImage(builder.mRawImage);
        setDownloaded(builder.mDownloaded);
    }

    protected ItemViewPager(Parcel in) {
        mId = in.readInt();
        mPath = in.readString();
        mUserName = in.readString();
        mLikeByUser = in.readByte() != 0;
        mImageId = in.readString();
        mRawImage = in.readString();
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getImageId() {
        return mImageId;
    }

    public void setImageId(String imageId) {
        mImageId = imageId;
    }

    public String getRawImage() {
        return mRawImage;
    }

    public void setRawImage(String rawImage) {
        mRawImage = rawImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mPath);
        dest.writeString(mUserName);
        dest.writeByte(mLikeByUser ? (byte) 1 : (byte) 0);
        dest.writeString(mImageId);
        dest.writeString(mRawImage);
        dest.writeByte(mDownloaded ? (byte) 1 : (byte) 0);
    }

    public static final class Builder {
        private String mPath;
        private String mUserName;
        private boolean mLikeByUser;
        private String mImageId;
        private String mRawImage;
        private boolean mDownloaded;
        private int mId;

        public Builder() {
        }

        public Builder mPath(String path) {
            mPath = path;
            return this;
        }

        public Builder mUserName(String userName) {
            mUserName = userName;
            return this;
        }

        public Builder mLikeByUser(boolean likeByUser) {
            mLikeByUser = likeByUser;
            return this;
        }

        public Builder mImageId(String imageId) {
            mImageId = imageId;
            return this;
        }

        public Builder mRawImage(String rawImage) {
            mRawImage = rawImage;
            return this;
        }

        public Builder mDownloaded(boolean downloaded) {
            mDownloaded = downloaded;
            return this;
        }

        public ItemViewPager build() {
            return new ItemViewPager(this);
        }

        public Builder mId(int id) {
            mId = id;
            return this;
        }
    }
}
