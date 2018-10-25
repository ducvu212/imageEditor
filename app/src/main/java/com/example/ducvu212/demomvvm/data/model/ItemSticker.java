package com.example.ducvu212.demomvvm.data.model;

/**
 * Created by CuD HniM on 18/10/24.
 */
public class ItemSticker {

    private int mId;
    private int mDrawable;

    public ItemSticker(int drawable, int id) {
        mId = id;
        mDrawable = drawable;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public void setDrawable(int drawable) {
        mDrawable = drawable;
    }
}
