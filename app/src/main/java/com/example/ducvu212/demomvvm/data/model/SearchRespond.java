package com.example.ducvu212.demomvvm.data.model;

import android.os.Parcel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SearchRespond {

    @SerializedName("total")
    @Expose
    private int mTotal;
    @SerializedName("total_pages")
    @Expose
    private int mTotalPages;
    @SerializedName("results")
    @Expose
    private ArrayList<Collection> mCollections;

    public SearchRespond() {
    }

    protected SearchRespond(Parcel in) {
        mTotal = in.readInt();
        mTotalPages = in.readInt();
        mCollections = in.createTypedArrayList(Collection.CREATOR);
    }

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
