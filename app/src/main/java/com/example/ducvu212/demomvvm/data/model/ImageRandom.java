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
    @ColumnInfo(name = "type")
    private String mType;

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
        setType(builder.mType);
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
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

    public static final class Builder {
        private String mPath;
        private String mUserName;
        private int mLikeByUser;
        private String mImageId;
        private String mRawImage;
        private int mDownloaded;
        private String mType;

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

        public Builder mType(String type) {
            mType = type;
            return this;
        }

        public ImageRandom build() {
            return new ImageRandom(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageId);
        dest.writeString(mPath);
        dest.writeString(mUserName);
        dest.writeInt(mLikeByUser);
        dest.writeInt(mDownloaded);
        dest.writeString(mRawImage);
        dest.writeString(mType);
    }

    protected ImageRandom(Parcel in) {
        mImageId = in.readString();
        mPath = in.readString();
        mUserName = in.readString();
        mLikeByUser = in.readInt();
        mDownloaded = in.readInt();
        mRawImage = in.readString();
        mType = in.readString();
    }

    public static final Creator<ImageRandom> CREATOR = new Creator<ImageRandom>() {
        @Override
        public ImageRandom createFromParcel(Parcel source) {
            return new ImageRandom(source);
        }

        @Override
        public ImageRandom[] newArray(int size) {
            return new ImageRandom[size];
        }
    };
}
