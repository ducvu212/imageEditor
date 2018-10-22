package com.example.ducvu212.demomvvm.data.model;

public class CustomPath extends android.graphics.Path {
    private int mColor;

    private android.graphics.Path mPath;

    private CustomPath(Builder builder) {
        setColor(builder.mColor);
        setPath(builder.mPath);
    }

    public android.graphics.Path getPath() {
        return mPath;
    }

    public void setPath(android.graphics.Path path) {
        mPath = path;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public static final class Builder {
        private int mColor;
        private android.graphics.Path mPath;

        public Builder() {
        }

        public Builder mColor(int color) {
            mColor = color;
            return this;
        }

        public Builder mPath(android.graphics.Path path) {
            mPath = path;
            return this;
        }

        public CustomPath build() {
            return new CustomPath(this);
        }
    }
}
