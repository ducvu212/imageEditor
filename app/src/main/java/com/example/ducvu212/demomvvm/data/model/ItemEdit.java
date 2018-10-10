package com.example.ducvu212.demomvvm.data.model;

/**
 * Created by CuD HniM on 18/10/10.
 */
public class ItemEdit {

    private String mName;
    private int mImageView;

    private ItemEdit(Builder builder) {
        mName = builder.mName;
        mImageView = builder.mImageView;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImageView() {
        return mImageView;
    }

    public void setImageView(int imageView) {
        mImageView = imageView;
    }

    public static final class Builder {
        private String mName;
        private int mImageView;

        public Builder() {
        }

        public Builder mName(String name) {
            mName = name;
            return this;
        }

        public Builder mImageView(int imageView) {
            mImageView = imageView;
            return this;
        }

        public ItemEdit build() {
            return new ItemEdit(this);
        }
    }
}
