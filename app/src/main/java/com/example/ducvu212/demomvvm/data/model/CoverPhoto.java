package com.example.ducvu212.demomvvm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CuD HniM on 18/10/04.
 */
public class CoverPhoto{
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
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("urls")
    @Expose
    private UrlImage mUrls;
    @SerializedName("links")
    @Expose
    private Links mLinks;
    @SerializedName("user")
    @Expose
    private User mUser;

    private CoverPhoto(Builder builder) {
        setId(builder.mId);
        setCreatedAt(builder.mCreatedAt);
        setUpdatedAt(builder.mUpdatedAt);
        setWidth(builder.mWidth);
        setHeight(builder.mHeight);
        setDescription(builder.mDescription);
        setUrls(builder.mUrls);
        setLinks(builder.mLinks);
        setUser(builder.mUser);
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
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

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public static final class Builder {
        private String mId;
        private String mCreatedAt;
        private String mUpdatedAt;
        private Integer mWidth;
        private Integer mHeight;
        private String mDescription;
        private UrlImage mUrls;
        private Links mLinks;
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

        public Builder mDescription(String description) {
            mDescription = description;
            return this;
        }

        public Builder mUrls(UrlImage urls) {
            mUrls = urls;
            return this;
        }

        public Builder mLinks(Links links) {
            mLinks = links;
            return this;
        }

        public Builder mUser(User user) {
            mUser = user;
            return this;
        }

        public CoverPhoto build() {
            return new CoverPhoto(this);
        }
    }
}
