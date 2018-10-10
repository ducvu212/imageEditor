package com.example.ducvu212.demomvvm.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by CuD HniM on 18/10/03.
 */
@Entity(tableName = "images")
public class ImageRandom implements Parcelable {

    public static final Creator<ImageRandom> CREATOR = new Creator<ImageRandom>() {
        @Override
        public ImageRandom createFromParcel(Parcel in) {
            return new ImageRandom(in);
        }

        @Override
        public ImageRandom[] newArray(int size) {
            return new ImageRandom[size];
        }
    };
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "image_id")
    private String mImageId;
    @ColumnInfo(name = "path")
    private String mPath;
    @ColumnInfo(name = "username")
    private String mUserName;
    @ColumnInfo(name = "like")
    private int mLikeByUser;
    @ColumnInfo(name = "download")
    private int mDownloaded;
    @ColumnInfo(name = "raw")
    private String mRawImage;

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public ImageRandom() {
    }

    private ImageRandom(Builder builder) {
        setPath(builder.mPath);
        setUserName(builder.mUserName);
        setLikeByUser(builder.mLikeByUser);
        setImageId(builder.mImageId);
        setRawImage(builder.mRawImage);
        setDownloaded(builder.mDownloaded);
    }

    protected ImageRandom(Parcel in) {
        mPath = in.readString();
        mUserName = in.readString();
        mImageId = in.readString();
        mRawImage = in.readString();
    }

    public int getLikeByUser() {
        return mLikeByUser;
    }

    public void setLikeByUser(int likeByUser) {
        mLikeByUser = likeByUser;
    }

    public int getDownloaded() {
        return mDownloaded;
    }

    public void setDownloaded(int downloaded) {
        mDownloaded = downloaded;
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
        dest.writeString(mPath);
        dest.writeString(mUserName);
        dest.writeString(mImageId);
        dest.writeString(mRawImage);
        dest.writeInt(mLikeByUser);
        dest.writeInt(mDownloaded);
    }

    public static final class Builder {
        private String mPath;
        private String mUserName;
        private int mLikeByUser;
        private String mImageId;
        private String mRawImage;
        private int mDownloaded;

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

        public Builder mLikeByUser(int likeByUser) {
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

        public Builder mDownloaded(int downloaded) {
            mDownloaded = downloaded;
            return this;
        }

        public ImageRandom build() {
            return new ImageRandom(this);
        }
    }
}
