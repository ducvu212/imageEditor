package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SearchRespond implements Parcelable {

    @SerializedName("total")
    @Expose
    private int mTotal;
    @SerializedName("total_pages")
    @Expose
    private int mTotalPages;
    @SerializedName("results")
    @Expose
    private ArrayList<Collection> mCollections;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mTotal);
        dest.writeInt(this.mTotalPages);
        dest.writeTypedList(this.mCollections);
    }

    public SearchRespond() {
    }

    protected SearchRespond(Parcel in) {
        this.mTotal = in.readInt();
        this.mTotalPages = in.readInt();
        this.mCollections = in.createTypedArrayList(Collection.CREATOR);
    }

    public static final Creator<SearchRespond> CREATOR = new Creator<SearchRespond>() {
        @Override
        public SearchRespond createFromParcel(Parcel source) {
            return new SearchRespond(source);
        }

        @Override
        public SearchRespond[] newArray(int size) {
            return new SearchRespond[size];
        }
    };

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public ArrayList<Collection> getCollections() {
        return mCollections;
    }

    public void setCollections(ArrayList<Collection> collections) {
        mCollections = collections;
    }
}
