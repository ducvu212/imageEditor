package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection {

    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("title")
    @Expose
    private String mTitle;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("published_at")
    @Expose
    private String mPublishedAt;
    @SerializedName("updated_at")
    @Expose
    private String mUpdatedAt;
    @SerializedName("featured")
    @Expose
    private Boolean mFeatured;
    @SerializedName("total_photos")
    @Expose
    private Integer mTotalPhotos;
    @SerializedName("cover_photo")
    @Expose
    private CoverPhoto mCoverPhoto;

    private Collection(Builder builder) {
        setId(builder.mId);
        setTitle(builder.mTitle);
        setDescription(builder.mDescription);
        setPublishedAt(builder.mPublishedAt);
        setUpdatedAt(builder.mUpdatedAt);
        setFeatured(builder.mFeatured);
        setTotalPhotos(builder.mTotalPhotos);
        setCoverPhoto(builder.mCoverPhoto);
    }

    protected Collection(Parcel in) {
        mId = (Integer) in.readValue(Integer.class.getClassLoader());
        mTitle = in.readString();
        mDescription = in.readString();
        mPublishedAt = in.readString();
        mUpdatedAt = in.readString();
        mFeatured = (Boolean) in.readValue(Boolean.class.getClassLoader());
        mTotalPhotos = (Integer) in.readValue(Integer.class.getClassLoader());
        mCoverPhoto = in.readParcelable(CoverPhoto.class.getClassLoader());
    }

    public CoverPhoto getCoverPhoto() {
        return mCoverPhoto;
    }

    public void setCoverPhoto(CoverPhoto coverPhoto) {
        mCoverPhoto = coverPhoto;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getPublishedAt() {
        return mPublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        mPublishedAt = publishedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getFeatured() {
        return mFeatured;
    }

    public void setFeatured(Boolean featured) {
        mFeatured = featured;
    }

    public Integer getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        mTotalPhotos = totalPhotos;
    }

    public static final class Builder{
        private Integer mId;
        private String mTitle;
        private String mDescription;
        private String mPublishedAt;
        private String mUpdatedAt;
        private Boolean mFeatured;
        private Integer mTotalPhotos;
        private CoverPhoto mCoverPhoto;

        public Builder() {
        }

        public Builder mId(Integer id) {
            mId = id;
            return this;
        }

        public Builder mTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder mDescription(String description) {
            mDescription = description;
            return this;
        }

        public Builder mPublishedAt(String publishedAt) {
            mPublishedAt = publishedAt;
            return this;
        }

        public Builder mUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
            return this;
        }

        public Builder mFeatured(Boolean featured) {
            mFeatured = featured;
            return this;
        }

        public Builder mTotalPhotos(Integer totalPhotos) {
            mTotalPhotos = totalPhotos;
            return this;
        }

        public Builder mCoverPhoto(CoverPhoto val) {
            mCoverPhoto = val;
            return this;
        }

        public Collection build() {
            return new Collection(this);
        }

        protected Builder(Parcel in) {
            mId = (Integer) in.readValue(Integer.class.getClassLoader());
            mTitle = in.readString();
            mDescription = in.readString();
            mPublishedAt = in.readString();
            mUpdatedAt = in.readString();
            mFeatured = (Boolean) in.readValue(Boolean.class.getClassLoader());
            mTotalPhotos = (Integer) in.readValue(Integer.class.getClassLoader());
            mCoverPhoto = in.readParcelable(CoverPhoto.class.getClassLoader());
        }
    }
}
