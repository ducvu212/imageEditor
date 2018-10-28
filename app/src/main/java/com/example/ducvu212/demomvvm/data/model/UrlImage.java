package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UrlImage implements Parcelable {

    public static final Creator<UrlImage> CREATOR = new Creator<UrlImage>() {
        @Override
        public UrlImage createFromParcel(Parcel source) {
            return new UrlImage(source);
        }

        @Override
        public UrlImage[] newArray(int size) {
            return new UrlImage[size];
        }
    };
    @SerializedName("raw")
    @Expose
    private String mRaw;
    @SerializedName("full")
    @Expose
    private String mFull;
    @SerializedName("regular")
    @Expose
    private String mRegular;
    @SerializedName("small")
    @Expose
    private String mSmall;
    @SerializedName("thumb")
    @Expose
    private String mThumb;

    private UrlImage(Builder builder) {
        setRaw(builder.mRaw);
        setFull(builder.mFull);
        setRegular(builder.mRegular);
        setSmall(builder.mSmall);
        setThumb(builder.mThumb);
    }

    protected UrlImage(Parcel in) {
        mRaw = in.readString();
        mFull = in.readString();
        mRegular = in.readString();
        mSmall = in.readString();
        mThumb = in.readString();
    }

    public String getRaw() {
        return mRaw;
    }

    public void setRaw(String raw) {
        mRaw = raw;
    }

    public String getFull() {
        return mFull;
    }

    public void setFull(String full) {
        mFull = full;
    }

    public String getRegular() {
        return mRegular;
    }

    public void setRegular(String regular) {
        mRegular = regular;
    }

    public String getSmall() {
        return mSmall;
    }

    public void setSmall(String small) {
        mSmall = small;
    }

    public String getThumb() {
        return mThumb;
    }

    public void setThumb(String thumb) {
        mThumb = thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRaw);
        dest.writeString(mFull);
        dest.writeString(mRegular);
        dest.writeString(mSmall);
        dest.writeString(mThumb);
    }

    public static final class Builder {
        private String mRaw;
        private String mFull;
        private String mRegular;
        private String mSmall;
        private String mThumb;

        public Builder() {
        }

        public Builder mRaw(String raw) {
            mRaw = raw;
            return this;
        }

        public Builder mFull(String full) {
            mFull = full;
            return this;
        }

        public Builder mRegular(String regular) {
            mRegular = regular;
            return this;
        }

        public Builder mSmall(String small) {
            mSmall = small;
            return this;
        }

        public Builder mThumb(String thumb) {
            mThumb = thumb;
            return this;
        }

        public UrlImage build() {
            return new UrlImage(this);
        }
    }
}