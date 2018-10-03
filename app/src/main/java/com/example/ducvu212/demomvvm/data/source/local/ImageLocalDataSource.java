package com.example.ducvu212.demomvvm.data.source.local;

import com.example.ducvu212.demomvvm.data.source.ImageDataSource;

/**
 * Created by CuD HniM on 18/10/03.
 */
public class ImageLocalDataSource implements ImageDataSource.ImageLocalDataSource {
    private static ImageLocalDataSource sInstance;

    public static synchronized ImageLocalDataSource getsInstance() {
        if (sInstance == null) {
            synchronized (ImageLocalDataSource.class) {
                if (sInstance == null) {
                    sInstance = new ImageLocalDataSource();
                }
            }
        }
        return sInstance;
    }
}
