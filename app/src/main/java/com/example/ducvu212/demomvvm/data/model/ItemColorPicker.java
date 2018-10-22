package com.example.ducvu212.demomvvm.data.model;

/**
 * Created by CuD HniM on 18/10/11.
 */
public class ItemColorPicker {

    private int mColor;
    private int mId;

    public ItemColorPicker(int color, int id) {
        mColor = color;
        mId = id;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
