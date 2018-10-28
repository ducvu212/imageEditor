package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links implements Parcelable {

    public static final Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };
    @SerializedName("self")
    @Expose
    private String mSelf;
    @SerializedName("html")
    @Expose
    private String mHtml;
    @SerializedName("download")
    @Expose
    private String mDownload;

    private Links(Builder builder) {
        setSelf(builder.mSelf);
        setHtml(builder.mHtml);
        setDownload(builder.mDownload);
    }

    private Links(Parcel in) {
        mSelf = in.readString();
        mHtml = in.readString();
        mDownload = in.readString();
    }

    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        mSelf = self;
    }

    public String getHtml() {
        return mHtml;
    }

    public void setHtml(String html) {
        mHtml = html;
    }

    public String getDownload() {
        return mDownload;
    }

    public void setDownload(String download) {
        mDownload = download;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSelf);
        dest.writeString(mHtml);
        dest.writeString(mDownload);
    }

    public static final class Builder {
        private String mSelf;
        private String mHtml;
        private String mDownload;

        public Builder() {
        }

        public Builder mSelf(String self) {
            mSelf = self;
            return this;
        }

        public Builder mHtml(String html) {
            mHtml = html;
            return this;
        }

        public Builder mDownload(String download) {
            mDownload = download;
            return this;
        }

        public Links build() {
            return new Links(this);
        }
    }
}