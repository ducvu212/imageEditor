package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection implements Parcelable {

    @SerializedName("mId")
    @Expose
    public int mId;
    @SerializedName("mTitle")
    @Expose
    public String mTitle;
    @SerializedName("mDescription")
    @Expose
    public Object mDescription;
    @SerializedName("mPublished_at")
    @Expose
    public String mPublishedAt;
    @SerializedName("mUpdated_at")
    @Expose
    public String mUpdatedAt;
    @SerializedName("mFeatured")
    @Expose
    public boolean mFeatured;
    @SerializedName("mTotal_photos")
    @Expose
    public int mTotalPhotos;
    @SerializedName("mImage")
    @Expose
    public Image mImage;
    public final static Parcelable.Creator<Collection> CREATOR = new Creator<Collection>() {
        public Collection createFromParcel(Parcel in) {
            return new Collection(in);
        }

        public Collection[] newArray(int size) {
            return (new Collection[size]);
        }
    };

    protected Collection(Parcel in) {
        this.mId = ((int) in.readValue((int.class.getClassLoader())));
        this.mTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.mDescription = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mPublishedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.mUpdatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.mFeatured = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.mTotalPhotos = ((int) in.readValue((int.class.getClassLoader())));
        this.mImage = ((Image) in.readValue((Image.class.getClassLoader())));
    }

    public String getUrlImageCollection() {
        String url = mImage.getUrls().getFull();
        return url;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
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

    public boolean isFeatured() {
        return mFeatured;
    }

    public void setFeatured(boolean featured) {
        mFeatured = featured;
    }

    public int getTotalPhotos() {
        return mTotalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        mTotalPhotos = totalPhotos;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public Collection() {
    }

    public Collection withMId(int mId) {
        this.mId = mId;
        return this;
    }

    public Collection withMTitle(String mTitle) {
        this.mTitle = mTitle;
        return this;
    }

    public Collection withMDescription(Object mDescription) {
        this.mDescription = mDescription;
        return this;
    }

    public Collection withMPublishedAt(String mPublishedAt) {
        this.mPublishedAt = mPublishedAt;
        return this;
    }

    public Collection withMUpdatedAt(String mUpdatedAt) {
        this.mUpdatedAt = mUpdatedAt;
        return this;
    }

    public Collection withMFeatured(boolean mFeatured) {
        this.mFeatured = mFeatured;
        return this;
    }

    public Collection withMTotalPhotos(int mTotalPhotos) {
        this.mTotalPhotos = mTotalPhotos;
        return this;
    }

    public Collection withMImage(Image mImage) {
        this.mImage = mImage;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mId);
        dest.writeValue(mTitle);
        dest.writeValue(mDescription);
        dest.writeValue(mPublishedAt);
        dest.writeValue(mUpdatedAt);
        dest.writeValue(mFeatured);
        dest.writeValue(mTotalPhotos);
        dest.writeValue(mImage);
    }

    public int describeContents() {
        return 0;
    }
}