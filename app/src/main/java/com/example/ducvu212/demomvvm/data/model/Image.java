package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("created_at")
    @Expose
    private String mCreatedAt;
    @SerializedName("updated_at")
    @Expose
    private String mUpdatedAt;
    @SerializedName("width")
    @Expose
    private Integer mWidth;
    @SerializedName("height")
    @Expose
    private Integer mHeight;
    @SerializedName("urls")
    @Expose
    private UrlImage mUrls;
    @SerializedName("links")
    @Expose
    private Links mLinks;
    @SerializedName("likes")
    @Expose
    private Integer mLikes;
    @SerializedName("liked_by_user")
    @Expose
    private Boolean mLikedByUser;
    @SerializedName("user")
    @Expose
    private User mUser;

    private String mPath;

    public Image(String path, String createdAt) {
        mPath = path;
        mCreatedAt = createdAt;
    }

    private Image(Builder builder) {
        setId(builder.mId);
        setCreatedAt(builder.mCreatedAt);
        setUpdatedAt(builder.mUpdatedAt);
        setWidth(builder.mWidth);
        setHeight(builder.mHeight);
        setUrls(builder.mUrls);
        setLinks(builder.mLinks);
        setLikes(builder.mLikes);
        setLikedByUser(builder.mLikedByUser);
        setUser(builder.mUser);
    }

    protected Image(Parcel in) {
        mId = in.readString();
        mCreatedAt = in.readString();
        mUpdatedAt = in.readString();
        mWidth = (Integer) in.readValue(Integer.class.getClassLoader());
        mHeight = (Integer) in.readValue(Integer.class.getClassLoader());
        mUrls = in.readParcelable(UrlImage.class.getClassLoader());
        mLinks = in.readParcelable(Links.class.getClassLoader());
        mLikes = (Integer) in.readValue(Integer.class.getClassLoader());
        mLikedByUser = (Boolean) in.readValue(Boolean.class.getClassLoader());
        mUser = in.readParcelable(User.class.getClassLoader());
        mPath = in.readString();
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Integer getWidth() {
        return mWidth;
    }

    public void setWidth(Integer width) {
        mWidth = width;
    }

    public Integer getHeight() {
        return mHeight;
    }

    public void setHeight(Integer height) {
        mHeight = height;
    }

    public UrlImage getUrls() {
        return mUrls;
    }

    public void setUrls(UrlImage urls) {
        mUrls = urls;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        mLinks = links;
    }

    public Integer getLikes() {
        return mLikes;
    }

    public void setLikes(Integer likes) {
        mLikes = likes;
    }

    public Boolean getLikedByUser() {
        return mLikedByUser;
    }

    public void setLikedByUser(Boolean likedByUser) {
        mLikedByUser = likedByUser;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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
        dest.writeString(mId);
        dest.writeString(mCreatedAt);
        dest.writeString(mUpdatedAt);
        dest.writeValue(mWidth);
        dest.writeValue(mHeight);
        dest.writeParcelable(mUrls, flags);
        dest.writeParcelable(mLinks, flags);
        dest.writeValue(mLikes);
        dest.writeValue(mLikedByUser);
        dest.writeParcelable(mUser, flags);
        dest.writeString(mPath);
    }

    public static final class Builder {
        private String mId;
        private String mCreatedAt;
        private String mUpdatedAt;
        private Integer mWidth;
        private Integer mHeight;
        private UrlImage mUrls;
        private Links mLinks;
        private Integer mLikes;
        private Boolean mLikedByUser;
        private User mUser;

        public Builder() {
        }

        public Builder mId(String id) {
            mId = id;
            return this;
        }

        public Builder mCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
            return this;
        }

        public Builder mUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
            return this;
        }

        public Builder mWidth(Integer width) {
            mWidth = width;
            return this;
        }

        public Builder mHeight(Integer height) {
            mHeight = height;
            return this;
        }

        public Builder mUrls(UrlImage urlImage) {
            mUrls = urlImage;
            return this;
        }

        public Builder mLinks(Links links) {
            mLinks = links;
            return this;
        }

        public Builder mLikes(Integer likes) {
            mLikes = likes;
            return this;
        }

        public Builder mLikedByUser(Boolean likedByUser) {
            mLikedByUser = likedByUser;
            return this;
        }

        public Builder mUser(User user) {
            mUser = user;
            return this;
        }

        public Image build() {
            return new Image(this);
        }
    }
}
